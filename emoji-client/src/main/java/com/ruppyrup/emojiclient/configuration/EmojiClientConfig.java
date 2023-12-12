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

    @Bean
    @Profile("https")
    public RestTemplate httpsRestTemplate(RestTemplateBuilder restTemplateBuilder, SslBundles sslBundles) {
        return restTemplateBuilder.setSslBundle(sslBundles.getBundle("emoji-api")).build();
    }

    @Bean
    @Profile("http")
    public RestTemplate restTemplate() {
        log.info("Creating http resttemplate");
        return new RestTemplate();
    }
}
