package com.ruppyrup.integrationtest;

import com.ruppyrup.emojiapi.EmojiApiApplication;
import com.ruppyrup.integrationtest.config.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = {IntegrationTestConfig.class, EmojiApiApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = {
        "spring.ssl.bundle.pem.emoji-test-client.truststore.certificate=classpath:emojiapicertlocal.pem"
    }
)
class IntegrationTest {

    @Value(value = "${server.port}")
    private int port;

    private final RestTemplate integrationRestTemplate;
    private String encodedString;
    private String decodedString;

    IntegrationTest(@Qualifier("integrationRestTemplate") RestTemplate integrationRestTemplate) {
        this.integrationRestTemplate = integrationRestTemplate;
    }


    @BeforeEach
    void setUp() {
        encodedString = "\uD83E\uDD21\uD83E\uDD75\uD83D\uDCA9\uD83D\uDCA9\uD83E\uDD16\uD83E\uDD2C\uD83D\uDC7D\uD83E\uDD16\uD83E\uDEE1\uD83D\uDCA9\uD83E\uDDD0";
        decodedString = "hello world";
    }

    @Test
    public void messageShouldBeEncodedCorrectly() throws Exception {
        assertThat(this.integrationRestTemplate.getForObject("https://localhost:" + port + "/emoji/encode/" + decodedString,
                String.class)).contains(encodedString);
    }

    @Test
    public void messageShouldBeDecodedCorrectly() throws Exception {
        assertThat(this.integrationRestTemplate.getForObject("https://localhost:" + port + "/emoji/decode/" + encodedString,
                String.class)).contains(decodedString);
    }
}