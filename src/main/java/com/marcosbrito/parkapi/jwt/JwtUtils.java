package com.marcosbrito.parkapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;


@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";
    //O token expira nesse periodo de tempo aqui:
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 2;


    private JwtUtils() {

    }

    private static Key generate() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date expiration(Date start) {
        LocalDateTime time = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = time.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    static JwtToken generateToken(String username, String role) {

        Date issuedAt = new Date(); // Gera a data atual como data de criação do token
        Date limit = expiration(issuedAt); // Calcula a data de expiração com base na data de criação

        // Adiciona as instruções para construir o token JWT
        String token = Jwts.builder() // Cria um token JWT utilizando o builder
                .setHeaderParam("typ", "JWT") // Define o cabeçalho indicando que é um token do tipo JWT
                .setSubject(username) // Define o 'subject' do token como o nome do usuário
                .setIssuedAt(issuedAt) // Define a data de emissão do token
                .setExpiration(limit) // Define a data de expiração do token
                .signWith(generate(), SignatureAlgorithm.HS256) // Assina o token com uma chave gerada e o algoritmo ES256
                .claim("role", role) // Adiciona uma claim personalizada com o papel do usuário
                .compact(); // Finaliza a construção do token e retorna uma string compactada

        return new JwtToken(token); // Retorna o token JWT encapsulado em uma classe JwtToken
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(generate()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        } catch (JwtException ex) {
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }
        return null;
    }

    public static String userInToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public static boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(generate()).build()
                    .parseClaimsJws(refactorToken(token));

            return true; //Se nenhuma exception ocorrer, o token é valido
        } catch (JwtException ex) {
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }
        return false;
    }

    private static String refactorToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }
}