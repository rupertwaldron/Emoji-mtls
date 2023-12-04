package com.ruppyrup.emojiapi.controller;

import com.ruppyrup.emojiapi.model.Message;
import com.ruppyrup.emojiapi.translator.Translator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("emoji")
public class EmojiController {

    private static final Logger logger = LogManager.getLogger(EmojiController.class);

    private final Translator emojiTranslator;

    public EmojiController(Translator emojiTranslator) {
        this.emojiTranslator = emojiTranslator;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/encode/{text}")
    public String encode(@PathVariable String text) {
        Message message = new Message(text);
        logger.info("Encoding message :: " + text);
        return message.encode(emojiTranslator);
    }

    @GetMapping("/decode/{emojis}")
    public String decode(@PathVariable String emojis) {
        Message message = new Message(emojis);
        logger.info("Decoding message :: " + emojis);
        return message.decode(emojiTranslator);
    }
}
