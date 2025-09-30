package com.stayhapa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")   // 👈 this tells Spring to load application-test.properties
class StayHapaApplicationTests {

    @Test
    void contextLoads() {
        // This just verifies the Spring context can start with test configs
    }
}
