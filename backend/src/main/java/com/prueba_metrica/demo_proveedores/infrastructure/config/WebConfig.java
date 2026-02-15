package com.prueba_metrica.demo_proveedores.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration for CORS (Cross-Origin Resource Sharing).
 * Allows the Angular frontend to access the REST API.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/clima/**")
                .allowedOrigins(
                        "http://localhost:4200",        // Angular dev server
                        "http://frontend:80"            // Docker frontend container
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
