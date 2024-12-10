package com.marcosbrito.parkapi.service;


import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.repository.UsuaruioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service

/*
 * O usar  @RequiredArgsConstructor, o lombok usa o construtor pra injetar a dependencia
 *
 * */
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuaruioRepository usuaruioRepository; //para que o lambok, é importante que eu

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuaruioRepository.save(usuario);
    }

    @Transactional(readOnly = true) //Essa anotation denuncia pro spring que esse metodo é exclusivo para consulta no DB
    public Usuario getUsuario(Long id) {
        return usuaruioRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid id: " + id));
    }
    //eu declares as minhas dependencias como final. Interessante né?

}


