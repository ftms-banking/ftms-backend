package com.ftms.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller that provides a basic health check endpoint
 * for the FTMS Backend service.
 * <p>
 * This endpoint can be used by monitoring tools or load balancers
 * to verify that the application is running and responsive.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Health check endpoint.
     * <p>
     * Returns a simple JSON response indicating that the
     * FTMS Backend service is up and running, along with
     * basic metadata like service name and version.
     * </p>
     *
     * @return a map containing health status details:
     *         <ul>
     *           <li>{@code status} - current application state</li>
     *           <li>{@code service} - service name</li>
     *           <li>{@code version} - API version</li>
     *         </ul>
     *
     * @example Response:
     * <pre>
     * {
     *   "status": "UP",
     *   "service": "FTMS Backend",
     *   "version": "v1"
     * }
     * </pre>
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
