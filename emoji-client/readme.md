# Emoji Client

## Introduction
Emoji Client is an application that takes user input from the browser and sends it to the Emoji Api to be encrypted or
decrypted using the Emoji Api's encryption and decryption algorithms.

## mTls
The Emoji Client and Emoji API communicate using mutual TLS (mTls). For this to occur the client must have 
of generated its public-private keypair. The public-key will be provided to the Emoji-Api as a certificate which is 
placed into its truststore. The Emoji Api's certificate must also be placed in the clients truststore which is 
what is required for normal Tls. 

### Generating the KeyPair
```commandline
 keytool -genkeypair -alias emoji-client -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore client-keystore-local.p12 -validity 365
```
- storetype - PKCS12 is the type of store which is fully encrypted and contains both public and private keys

The public key / certificate can be extracted:
```commandline
keytool -exportcert -rfc -keystore client-keystore-local.p12 -storetype PKCS12 -storepass password -alias emoji-client -file consumercert.pem
```
- storepass sets the password which in this case is password
- alias - matches the alias in the PKCS12 file
- consumercert.pem contains the certificate in human readable form, but is still encrypted

The private key is required for the mtls communication by the client, this is extracted from the PKCS12 file like so:
```commandline
openssl pkcs12 -in client-keystore-local.p12 -out consumer-private-key.pem -nocerts
```

### Populating the truststores

The client certificate will need to be added to the Emoji-Api truststore so that it will accept the connection
from the client which is trusted
```commandline
keytool -import -alias emoji-client -file consumercert.pem -storetype JKS -keystore emoji-api.truststore
```

The certificate from the Emoji-api will need to be added to the client configuration. The springboot properties 
just require the certificate to be specified is using pem format.

### Springboot properties required for mtls client connection
```yaml
spring.ssl.bundle.pem.consumer-mtls.keystore.certificate=classpath:./consumer-certs/consumercert.pem
spring.ssl.bundle.pem.consumer-mtls.keystore.private-key=classpath:./consumer-certs/consumer-private-key.pem
spring.ssl.bundle.pem.consumer-mtls.keystore.private-key-password=${mtls.private-key-passwd}
spring.ssl.bundle.pem.consumer-mtls.truststore.certificate=classpath:./consumer-certs/apicert.pem
```

This is currently specified in the mtls-client-lib. The Emoji-client just needs to specify the follow properties 
once the mtls-client-lib is brought in:
```yaml
        "mtls.enabled=true",
        "mtls.private-key-passwd=password"
```
where `password` is the password using used when creating the public-private keypair.

The mtls-client-lib provides the mtlsRestTemplate for the clients to communicate using mtls easily.