package com.marcosbrito.parkapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocOpenApiConfig {


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization", securityScheme()))
                .info(new Info().title("REST-Api Documentation - spring Park").description("Api para gestao de estacionamento em um park").version("1.0.0").contact(new Contact().name("Marcos Brito").email("marcosbryto92@gmail.com")));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme().description("API para gestao de estacionamento em um park")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

    }
}
