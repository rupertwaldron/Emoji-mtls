package com.ruppyrup.emojiclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        properties = {
                "mtls.enabled=true",
                "mtls.private-key-password=password"
        })
@ActiveProfiles("https")
class EmojiClientApplicationTests {

    @Test
    void contextLoads() {
    }

}
