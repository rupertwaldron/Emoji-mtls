package com.ruppyrup.emojiclient.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("emoji-client")
public class EmojiClientController {

    @Value("${server.port}")
    private String port;

    private final RestTemplate restTemplate;

    private static final Logger logger = LogManager.getLogger(EmojiClientController.class);

    public EmojiClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/encode/{text}")
    public String encode(@PathVariable String text) {
        String response = restTemplate.getForObject("https://localhost:8443/emoji/encode/" + text,
                String.class);
        logger.info("Encode response = {}", response);
        return response;
    }

    @GetMapping("/decode/{emojis}")
    public String decode(@PathVariable String emojis) {
        String response = restTemplate.getForObject("https://localhost:8443/emoji/decode/" + emojis,
                String.class);
        logger.info("Decode response = {}", response);
        return response;
    }
}
