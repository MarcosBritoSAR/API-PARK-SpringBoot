package com.marcosbrito.parkapi.jwt;

import com.marcosbrito.parkapi.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/*
-- Processo referente ao Spring Security
Precisamos ter um objeto que extenda a interfacce User. Porque é esse o objetoq que o sprinng utiliza para armazenar as informacoes do usuairo logado
 */
public class JwtUserDetails extends User {

    // Um objeto da classe Usuario é mantido para armazenar os detalhes adicionais do usuário.
    private Usuario usuario;

    // Construtor que recebe um objeto Usuario e inicializa o objeto User do Spring Security.
    public JwtUserDetails(Usuario usuario) {
        // Chama o construtor da classe pai (User) para inicializar os detalhes padrão do Spring Security:
        // - Nome de usuário (username)
        // - Senha (password)
        // - Lista de roles (autoridades), convertidas para o formato esperado pelo Spring Security.
        super(
                usuario.getUsername(), // Nome de usuário do objeto Usuario
                usuario.getPassword(), // Senha do objeto Usuario
                AuthorityUtils.createAuthorityList(usuario.getRole().name()) // Converte o papel (role) do usuário para uma lista de autoridades
        );

        // Armazena o objeto Usuario recebido no atributo local para acesso posterior.
        this.usuario = usuario;
    }

    // Metodo para retornar o ID do usuário, obtido do objeto Usuario.
    public Long getId() {
        return this.usuario.getId();
    }

    // Metodo para retornar o papel (role) do usuário como uma String,
    // obtido do objeto Usuario.
    public String getRole() {
        return this.usuario.getRole().name();
    }
}