package com.marcosbrito.parkapi;

import com.marcosbrito.parkapi.jwt.JwtToken;
import com.marcosbrito.parkapi.web.dto.UsuarioLoginDto;
import com.marcosbrito.parkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//O Sring gerencia o nosso teste criando um ambiente de testes
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //anotacoes referentes aos scripts da tabela ususairo - popula
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // Remove os usuarios do banco de testes
public class AutenticacaoIT {

    @Autowired
    WebTestClient testClient; //Usado para trabalhar com objetos de testes


    @Test
    public void autenticar_ComCredenciaisValidas_RetornarTokenComStatus200(){
        JwtToken response = testClient.post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();


    }

    @Test
    public void autenticar_ComCredenciaisInvalidas_RetornarErrorMensageStatus400(){
        ErrorMessage response = testClient.post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("invalid@email.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(400);


         response = testClient.post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@email.com", "000000"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(400);


    }
}