package com.marcosbrito.parkapi.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
       log.info("Http Status 401 {}", authException.getMessage());
        //Trabalhando a resposta com status 401
        //Qundo o usuario nao for autenticado ele receberr o cabecalho www-authenticate" informando que ele tera que criar um token do tipo Bearer e mandalo para /api/v1/auth
        response.setHeader("www-authenticate", "Bearer realm='/api/v1/auth'");//Vou deixar pra testar
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
}
