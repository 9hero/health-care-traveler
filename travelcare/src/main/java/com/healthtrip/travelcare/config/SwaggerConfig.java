package com.healthtrip.travelcare.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/api/**")
                .build();
    }
    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .pathsToMatch("/api/admin/**")
                .build();
    }
    @Bean
    public OpenAPI openAPI() {
        SecurityScheme jwtAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer").bearerFormat("Bearer :'jwt'");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("jwtAuth");
        return new OpenAPI()
                .info(new Info().title("TravelCare API")
                        .description("TravelCare API 명세서입니다.")
                        .version("v0.0.5"))
                .components(new Components().addSecuritySchemes("jwtAuth", jwtAuth))
                .addSecurityItem(securityRequirement);
    }

}