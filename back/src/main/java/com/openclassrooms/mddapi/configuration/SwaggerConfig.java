package com.openclassrooms.mddapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    /**
     * Create documentation for the API
     * 
     * @return a documented API on "/swagger-ui/**" and "/v3/api-docs/**"
     */
    public OpenAPI rentalPortalOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("JavaInUse Authentication Service"))
                .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components(new Components().addSecuritySchemes("JavaInUseSecurityScheme",
                        new SecurityScheme()
                                .name("JavaInUseSecurityScheme")
                                .type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT")));

    }
}
