// Importação de pacotes e classes necessárias para implementar o filtro JWT.

package com.marcosbrito.parkapi.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Classe responsável por interceptar requisições HTTP para autenticação via JWT.
// Extende OncePerRequestFilter para garantir que o filtro seja executado uma vez por requisição.
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    // Serviço para carregar os detalhes do usuário baseado no nome de usuário.
    private final JwtUserDetailsService detailsService;

    // Método principal que processa a autenticação JWT em cada requisição HTTP.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrai o token JWT do cabeçalho da requisição.
        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        // Verifica se o token é nulo ou não começa com o prefixo esperado ("Bearer ").
        if (token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
            log.info("JWT Token está nulo, vazio ou não iniciado com 'Bearer '.");
            filterChain.doFilter(request, response); // Encaminha a requisição para o próximo filtro.
            return; // Interrompe o processamento deste filtro.
        }

        // Verifica se o token é válido usando a utilidade `JwtUtils`.
        if (!JwtUtils.validateToken(token)) {
            log.warn("JWT Token está inválido ou expirado.");
            filterChain.doFilter(request, response); // Encaminha a requisição para o próximo filtro.
            return; // Interrompe o processamento deste filtro.
        }

        // Extrai o nome de usuário contido no token JWT.
        String username = JwtUtils.userInToken(token);

        // Realiza o processo de autenticação no contexto do Spring Security.
        toAuthentication(request, username);

        // Encaminha a requisição para o próximo filtro na cadeia.
        // Se chegou até aqui, o token é válido e o usuário foi autenticado com sucesso.
        filterChain.doFilter(request, response);
    }

    // Metodo privado que realiza a autenticação no contexto do Spring Security.
    private void toAuthentication(HttpServletRequest request, String username) {

        // Carrega os detalhes do usuário a partir do banco de dados ou outra fonte.
        UserDetails userDetails = detailsService.loadUserByUsername(username);

        // Cria um token de autenticação autenticado com os detalhes do usuário e suas autoridades.
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        // Associa o request atual ao token de autenticação.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Define o token de autenticação no contexto de segurança do Spring Security.
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}