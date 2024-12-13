package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

//O Spring entende um Enum como um array, entao eu nao posso simplimente passar um nome e esperar o enum desse nome
    @Query("select u.role from Usuario u where u.username like :username")
    Usuario.Role findRoleByUsername(String username);
}
