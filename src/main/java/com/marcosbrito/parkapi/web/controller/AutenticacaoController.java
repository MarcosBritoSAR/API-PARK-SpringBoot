package com.marcosbrito.parkapi.web.controller;

//Criando um endPoint para a autenticacaoController

import com.marcosbrito.parkapi.jwt.JwtToken;
import com.marcosbrito.parkapi.jwt.JwtUserDetailsService;
import com.marcosbrito.parkapi.web.dto.UsuarioLoginDto;
import com.marcosbrito.parkapi.web.dto.UsuarioResponseDto;
import com.marcosbrito.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "contem todas informacoes relativas ao recurso de cadastro, edição e leitura de usuairo")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AutenticacaoController {

    public final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Autenticar na API", description = "Recurso de autenticação na API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticao realizada com sucesso, retorno de um Bearer Token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/auth")
    // Metodo para autenticar o usuário utilizando as credenciais fornecidas.
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto, HttpServletRequest request) {
        // Loga o início do processo de autenticação, incluindo as informações do DTO recebido.
        log.info("Processo de autenticacao pelo login{}", usuarioLoginDto.toString());

        try {
            // Cria um objeto de autenticação contendo o nome de usuário e senha do DTO.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken( //Verifica se existe um usiario som essa senha - Se nao existir, lanca uma exception
                            usuarioLoginDto.getUsername(), // Nome de usuário fornecido
                            usuarioLoginDto.getPassword()  // Senha fornecida
                    );

            // Tenta autenticar o usuário utilizando o gerenciador de autenticação.
            authenticationManager.authenticate(authenticationToken);

            // Gera um token JWT para o usuário autenticado, utilizando o serviço de detalhes do usuário.
            JwtToken jwtToken = jwtUserDetailsService.getTokenAuthenticated(usuarioLoginDto.getUsername());

            // Retorna uma resposta HTTP 200 (OK) contendo o token JWT gerado.
            return ResponseEntity.ok(jwtToken);
        } catch (AuthenticationException e) {
            // Loga uma mensagem de aviso caso ocorra uma falha na autenticação.
            log.warn("Falha no processo de autenticacao pelo login{}", usuarioLoginDto.toString());
        }

        // Caso a autenticação falhe, retorna uma resposta HTTP 400 (BAD REQUEST)
        // com uma mensagem de erro personalizada.
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(
                        request, // O objeto da requisição original
                        HttpStatus.BAD_REQUEST, // Código HTTP 400
                        "Credenciais invalidas" // Mensagem de erro retornada ao cliente
                ));
    }
}
