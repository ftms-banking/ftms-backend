package com.ftms.backend.shared.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Provides a health check endpoint for the FTMS Backend service.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Returns basic health information of the service.
     *
     * @return a map containing status, service name, and version
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "FTMS Backend",
                "version", "v1"
        );
    }
}
