package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.Usuario;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuaruioRepository extends JpaRepository<Usuario, Long> {
}
