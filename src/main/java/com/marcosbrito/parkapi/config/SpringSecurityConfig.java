package com.marcosbrito.parkapi.config;

import com.marcosbrito.parkapi.jwt.JwtAuthenticationEntryPoint;
import com.marcosbrito.parkapi.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration // Marca a classe como uma configuração do Spring
@EnableMethodSecurity // Habilita a segurança de métodos (por exemplo, @PreAuthorize, @Secured)
@EnableWebMvc // Habilita o suporte ao Spring MVC, necessário para controlar a configuração de segurança
public class SpringSecurityConfig {

    // Define uma lista de endpoints relacionados à documentação da API que serão acessíveis sem autenticação
    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-park.html", "/docs-park/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };

    // Configura o filtro de segurança para a aplicação
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF, geralmente necessário em APIs REST
                .formLogin(form -> form.disable()) // Desabilita o login baseado em formulário
                .httpBasic(basic -> basic.disable()) // Desabilita a autenticação básica HTTP (ex. cabeçalhos de autenticação)
                .authorizeHttpRequests(auth -> auth
                        // Permite o acesso sem autenticação para o POST de criação de usuários
                        .requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()
                        // Permite o acesso sem autenticação para o POST de autenticação (login)
                        .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()
                        // Permite o acesso aos caminhos relacionados à documentação OpenAPI sem autenticação
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        // Exige autenticação para todas as outras requisições
                        .anyRequest().authenticated()
                ).sessionManagement(
                        // Configura o gerenciamento de sessão para ser stateless (sem estado), ideal para APIs REST
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        // Adiciona um filtro JWT antes do filtro de autenticação padrão do Spring
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex
                        // Define o ponto de entrada para a autenticação em caso de falha (retorna erro 401)
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                ).build();
    }

    // Cria um bean do filtro JWT para autorizar requisições baseadas no token JWT
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    // Cria um bean de codificador de senha usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cria um bean do AuthenticationManager, necessário para a autenticação baseada em Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

