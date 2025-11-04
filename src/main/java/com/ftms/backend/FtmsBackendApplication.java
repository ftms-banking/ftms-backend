package com.ftms.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FtmsBackendApplication {
    /**
     * The main entry point for the FTMS Backend Spring Boot application.
     *
     * @param args command-line arguments (if any)
     */
    public static void main(final String[] args) {
        SpringApplication.run(FtmsBackendApplication.class, args);
    }
}
