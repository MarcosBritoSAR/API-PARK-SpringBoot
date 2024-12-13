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

@Configuration
@EnableMethodSecurity
@EnableWebMvc

public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //A partir desse retorno, eu defino todos as operacoes do filtro

        /*
        Como estamos trabalhando com spring security, precisamos desabilitar algumas coisas
        */

        return http
                .csrf(scrf -> scrf.disable())
                .formLogin(form -> form.disable())//Desabilitando formulario de login
                .httpBasic(basic -> basic.disable())//Desabilitando a autenticação do spring security
                .authorizeHttpRequests(auth-> auth//incluindo um bloco de autoricao
                        .requestMatchers(HttpMethod.POST, "api/v1/usuarios").permitAll()//Autorizo a qualquer usuario acessar usuarios e usar o metodo post para criar um novo usuario
                        .requestMatchers(HttpMethod.POST, "api/v1/auth").permitAll()//Torno publica o metodo de autentifaicao4
                        .anyRequest().authenticated() //Com excecao do metodo acima, todos os outros precisaram ter acesso para realizar operacoes
                ).sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Politica da sessão é do tipo stateless
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(
                        ex-> ex
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) //Sempre que houver algum problema referente a autentificacao do usuario, o spring entrara na classe JwtAuthenticationEntryPoint e  lancara a exception 401
                ).build();
    }


    @Bean //Configurando o tipo de criptografia
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Uma das criptografias mais seguras
    }

    @Bean //Referente ao gerencimento de autenticacao
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }
}
