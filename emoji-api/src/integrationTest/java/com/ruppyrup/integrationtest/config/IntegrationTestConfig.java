package com.ruppyrup.integrationtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IntegrationTestConfig {

//    @Value("${trust.store}")
//    private Resource trustStore;
//
//    @Value("${trust.password}")
//    private String trustStorePassword;

//    @Bean("integrationRestTemplate")
//    public RestTemplate integrationRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
//            CertificateException, MalformedURLException, IOException {
//
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
//                .build();
//        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConFactory).build();
//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(requestFactory);
//    }

    @Bean("integrationRestTemplate")
    public RestTemplate integrationRestTemplate(RestTemplateBuilder restTemplateBuilder, SslBundles sslBundles) {
        return restTemplateBuilder.setSslBundle(sslBundles.getBundle("emoji-test-client")).build();
    }

}
