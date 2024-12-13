package com.marcosbrito.parkapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<String> { //Vamos salvar o nome do usuario entao precisamos que o tipo generico seja string
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //Pegando o nome do usuario autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.of(authentication.getName()); //Retorna o nome do usuario
        }
        return null;
    }
}
