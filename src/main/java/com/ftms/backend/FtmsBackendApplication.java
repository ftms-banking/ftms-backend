package com.ftms.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the FTMS Backend Application.
 * <p>
 * This class bootstraps the Spring Boot context.
 */
@SpringBootApplication
public class FtmsBackendApplication {

    /** Private constructor to prevent instantiation. */
    private FtmsBackendApplication() {
        // Prevent instantiation
    }

    /**
     * The main entry point for the FTMS Backend Spring Boot application.
     *
     * @param args command-line arguments (if any)
     */
    public static void main(final String[] args) {
        SpringApplication.run(FtmsBackendApplication.class, args);
    }
}
