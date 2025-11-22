package com.example.Server.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        // 🔒 JWT Security Scheme 설정 추가
        SecurityScheme securityScheme = new SecurityScheme()
                .name("BearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("Neordinary 9th API")
                        .description("Neordinary 9th API 명세서입니다.")
                        .version("v1.0.0")
                );
    }
}
