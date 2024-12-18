package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService clienteVaga;

}
