package com.ftms.backend.shared.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller exposing health-related endpoints for the FTMS backend.
 *
 * <p>This controller is final because it is not designed to be subclassed.
 * For custom behavior use composition or delegate to a service bean.
 */
@RestController
@RequestMapping("/api/v1")
public final class HealthController {

    /**
     * Simple health endpoint used by load balancers and orchestration
     * systems to verify that the application is running.
     *
     * @return 200 OK when the application is healthy
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "FTMS Backend",
                "version", "0.0.1-SNAPSHOT"
        );
    }
}
