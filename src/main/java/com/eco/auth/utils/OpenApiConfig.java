package com.eco.auth.utils;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                // =====================================================
                // API INFORMATION
                // =====================================================
                .info(new Info()
                        .title("Auth Service API")
                        .version("1.0"))

                // =====================================================
                // API GATEWAY
                // Swagger "Try it out" will call Gateway :8080
                // instead of directly calling Auth Service :8081
                // =====================================================
                .servers(List.of(
                        new Server()
                                .url("/")
                                .description("API Gateway")
                ))

                // =====================================================
                // JWT SECURITY
                // =====================================================
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("BearerAuth")
                )

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "BearerAuth",
                                        new SecurityScheme()
                                                .name("BearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}