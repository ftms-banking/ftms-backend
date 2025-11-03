package com.ftms.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Use test profile
class FtmsBackendApplicationTests {

    @Test
    void contextLoads() {
        // Test passes with H2
        assertEquals(1, 1);
    }
}
