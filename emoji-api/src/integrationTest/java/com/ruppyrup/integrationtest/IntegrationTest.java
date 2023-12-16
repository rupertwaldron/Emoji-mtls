package com.ruppyrup.integrationtest;

import com.ruppyrup.emojiapi.EmojiApiApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = {EmojiApiApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = {
            "mtls.enabled=true",
            "mtls.private-key-passwd=password"
    }
)
class IntegrationTest {

    @Value(value = "${server.port}")
    private int port;

    private final RestTemplate mtlsRestTemplate;
    private String encodedString;
    private String decodedString;

    IntegrationTest(@Qualifier("mtlsRestTemplate") RestTemplate mtlsRestTemplate) {
        this.mtlsRestTemplate = mtlsRestTemplate;
    }


    @BeforeEach
    void setUp() {
        encodedString = "\uD83E\uDD21\uD83E\uDD75\uD83D\uDCA9\uD83D\uDCA9\uD83E\uDD16\uD83E\uDD2C\uD83D\uDC7D\uD83E\uDD16\uD83E\uDEE1\uD83D\uDCA9\uD83E\uDDD0";
        decodedString = "hello world";
    }

    @Test
    public void messageShouldBeEncodedCorrectly() throws Exception {
        assertThat(mtlsRestTemplate.getForObject("https://localhost:" + port + "/emoji/encode/" + decodedString,
                String.class)).contains(encodedString);
    }

    @Test
    public void messageShouldBeDecodedCorrectly() throws Exception {
        assertThat(mtlsRestTemplate.getForObject("https://localhost:" + port + "/emoji/decode/" + encodedString,
                String.class)).contains(decodedString);
    }
}