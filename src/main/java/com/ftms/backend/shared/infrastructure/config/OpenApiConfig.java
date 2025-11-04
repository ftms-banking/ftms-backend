package com.ftms.backend.shared.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * Builds the OpenAPI description for FTMS.
     *
     * <p>Adjust this bean if you need to change title/version or add
     * contact/license metadata.
     *
     * @return initialized OpenAPI instance
     */
    @Bean
    public OpenAPI ftmsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FTMS - Financial Transaction Management System API")
                        .description("Enterprise-grade REST API for managing customers, accounts, and transactions")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FTMS Development Team")
                                .email("dev@ftms.com")
                                .url("https://github.com/ftms-banking"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + "/api/v1")
                                .description("Local development"),
                        new Server()
                                .url("http://localhost:8090/api/v1")
                                .description("Local Docker")))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT Bearer token authentication")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }
}