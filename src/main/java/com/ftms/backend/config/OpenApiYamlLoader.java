package com.ftms.backend.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class OpenApiYamlLoader {

    /**
     * Loads and registers the OpenAPI specification from a YAML file located in the classpath.
     * This configuration enables Swagger UI to display API documentation defined in a static YAML file
     * rather than relying on annotations.
     *
     * @return the {@link OpenAPI} object deserialized from the ftms-api.yaml file
     * @throws Exception if the YAML file cannot be found, read, or parsed correctly
     */
    @Bean
    public OpenAPI ftmsOpenApiFromYaml() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        // allow enum values like "http", "Http" to map to HTTP
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);

        ClassPathResource res = new ClassPathResource("openapi/ftms-api.yaml");
        try (var is = res.getInputStream()) {
            return mapper.readValue(is, OpenAPI.class);
        }
    }
}
