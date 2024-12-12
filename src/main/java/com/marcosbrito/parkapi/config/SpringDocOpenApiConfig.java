package com.marcosbrito.parkapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("REST-Api Documentation - spring Park").description("Api para gestao de estacionamento em um park").version("1.0.0").contact(new Contact().name("Marcos Brito").email("marcosbryto92@gmail.com")));
    }
}
