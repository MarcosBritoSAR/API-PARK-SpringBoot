package com.marcosbrito.parkapi.service;


import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.repository.UsuaruioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
/*
* O usar  @RequiredArgsConstructor, o lombok usa o construtor pra injetar a dependencia
*
* */
public class UsuarioService {

    private final UsuaruioRepository usuaruioRepository; //para que o lambok, é importante que eu

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuaruioRepository.save(usuario);
    }
    //eu declares as minhas dependencias como final. Interessante né?

}
