package com.marcosbrito.parkapi.service;


import com.marcosbrito.parkapi.repository.UsuaruioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/*
* O usar  @RequiredArgsConstructor, o lombok usa o construtor pra injetar a dependencia
*
* */
public class UsuarioService {

    private final UsuaruioRepository usuaruioRepository; //para que o lambok, é importante que eu 
    //eu declares as minhas dependencias como final. Interessante né? 

}
