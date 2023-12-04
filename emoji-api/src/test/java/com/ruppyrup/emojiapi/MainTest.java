package com.ruppyrup.emojiapi;

import com.ruppyrup.emojiapi.translator.EmojiTranslator;
import com.ruppyrup.emojiapi.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    private String encodedString;
    private String decodedString;
    private EmojiTranslator translator;

    @BeforeEach
    void setUp() {
        encodedString = "\uD83E\uDD21\uD83E\uDD75\uD83D\uDCA9\uD83D\uDCA9\uD83E\uDD16\uD83E\uDD2C\uD83D\uDC7D\uD83E\uDD16\uD83E\uDEE1\uD83D\uDCA9\uD83E\uDDD0";
        decodedString = "hello world";
        translator = new EmojiTranslator();
    }

    @Test
    void canEncodeInput() {
        Message message = new Message(decodedString);
        String encode = message.encode(translator);
        assertEquals(encodedString, encode);
    }

    @Test
    void canDecodeInput() {
        Message encodedMessage = new Message(encodedString);
        String decode = encodedMessage.decode(translator);
        assertEquals(decodedString, decode);
    }

}