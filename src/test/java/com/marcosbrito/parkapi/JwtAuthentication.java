package com.marcosbrito.parkapi;

import com.marcosbrito.parkapi.jwt.JwtToken;
import com.marcosbrito.parkapi.web.dto.UsuarioLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorizaion(WebTestClient webTestClient, String userName, String password) {
        // Faz uma requisição POST para a URI "api/v1/auth" com os dados do usuário (username e senha)
        String token = webTestClient
                .post() // Define o método HTTP como POST
                .uri("api/v1/auth") // Define a URI para autenticação
                .bodyValue(new UsuarioLoginDto(userName, password)) // Define o corpo da requisição com um objeto UsuarioLoginDto
                .exchange() // Envia a requisição e recebe a resposta
                .expectStatus().isOk() // Verifica se o status da resposta é 200 (OK)
                .expectBody(JwtToken.class) // Espera que o corpo da resposta seja um objeto da classe JwtToken
                .returnResult() // Retorna o resultado da resposta
                .getResponseBody() // Obtém o corpo da resposta como objeto JwtToken
                .getToken(); // Extrai o token do objeto JwtToken

        // Retorna um Consumer que adiciona o token de autorização nos headers da requisição
        return header -> header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}

