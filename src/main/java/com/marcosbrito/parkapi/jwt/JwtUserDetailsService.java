package com.marcosbrito.parkapi.jwt;

import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;


    @Override
    //Busco o usuario e o retorno no formato de userDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return new JwtUserDetails(usuario);
    }

    //Usado para quando o usuario precisar autenticar. O usuario loga com usuario e senha. Caso seja verificado a autenticidade, precisarei gerar um token no corpo na requisicao
    public JwtToken getTokenAuthenticated(String username) {
        Usuario.Role role = usuarioService.buscarRolePorUsername(username); //retorna o perfil do usuario.
        return JwtUtils.generateToken(username, role.name().substring("ROLE_".length()));
    }
}
