package com.ruppyrup.emojiclient.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Profile("http")
    public RestTemplate restTemplate() {
        log.info("Creating http resttemplate");
        return new RestTemplate();
    }
}
