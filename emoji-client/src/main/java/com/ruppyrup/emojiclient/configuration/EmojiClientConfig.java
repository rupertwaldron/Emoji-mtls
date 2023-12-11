package com.ruppyrup.emojiclient.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class EmojiClientConfig {

//    @Value("${trust.store}")
//    private Resource trustStore;
//
//    @Value("${trust.password}")
//    private String trustStorePassword;

//    @Bean
//    @Profile("https")
//    public RestTemplate httpsRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
//            CertificateException, MalformedURLException, IOException {
//
//
//        log.info("Creating https resttemplate");
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
//                .build();
//        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
//
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConFactory).build();
//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(requestFactory);
//    }

    @Bean
    @Profile("https")
    public RestTemplate httpsRestTemplate(RestTemplateBuilder restTemplateBuilder, SslBundles sslBundles) {
        return restTemplateBuilder.setSslBundle(sslBundles.getBundle("emoji-client")).build();
    }

    @Bean
    @Profile("http")
    public RestTemplate restTemplate() {
        log.info("Creating http resttemplate");
        return new RestTemplate();
    }
}
