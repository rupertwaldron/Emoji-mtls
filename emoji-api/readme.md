# Emoji API - mTls Demonstration

## Introduction
The Emoji Api is an api that uses full mutual TLS (mTls) to communicate with the consumers of the api. The api 
demonstrates the Spring Boot 3.2 settings in the application.yml that are required by the api. No code changes 
are required

## Spring Boot Settings
`application.yml`

```commandline
spring:
  ssl:
    bundle:
      jks:
        emoji-api:
          key:
            alias: "emoji-api"
          keystore:
            location: "classpath:server-keystore-local.p12"
            password: "password"
            type: "PKCS12"
          truststore:
            type: "JKS"
            location: "classpath:emoji-api.truststore"
            password: "password"
            
server:
  port: 8443
  ssl:
    bundle: "emoji-api" // the name of the ssl bundle as specified above
#    enabled-protocols: TLSv1.3 // specify the tls protocol
#    client-auth: none // turns off mtls
    client-auth: need // requires the api to have the client's certificate in its trust store
```

The ssl bundle is of jks (Java Keystore) type which contain public key certificates and private keys. The 
bundle name is "emoji-api" which is then used to tell the server which bundle to use `server.ssl.bundle.emoji-api`.

Port 8443 is typically used for ssl communication in spring boot apps.

### Generating the certificates

The ssl bundle has the keystore and password and also the truststore and password. The keystore is generated like so:
```commandline
keytool -genkeypair -alias emoji-api -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore server-keystore-local.p12 -validity 365
```
This creates the pkcs12 file which contains a certificate that is valid for 365 days

The api certificate can be extracted and provided to the client for their trust store:
```commandline
keytool -exportcert -rfc -keystore server-keystore-local.p12 -storetype PKCS12 -storepass password -alias emoji-api -file apicert.pem
```

For mTls the client's certificate must be added to the api's truststore, this can be done like so
```commandline
keytool -import -alias emoji-client -file emojiclientcertlocal.pem -storetype JKS -keystore emoji-api.truststore
```



