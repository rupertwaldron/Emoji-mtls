package com.ruppyrup.emojiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmojiApiApplication {

    public static void main(String[] args) {
        System.out.println("Starting Emoji Application");
        SpringApplication.run(EmojiApiApplication.class, args);
    }

}
