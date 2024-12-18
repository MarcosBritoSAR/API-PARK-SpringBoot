package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.exception.EntityNotFoundException;
import com.marcosbrito.parkapi.repository.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteVagaService {

    private final ClienteVagaRepository clienteVagaRepository;

    @Transactional
    public ClienteVaga save(ClienteVaga clienteVaga) {
        return clienteVagaRepository.save(clienteVaga);
    }

@Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
            //Se a saida está vazia, o cliente ainda está ocupando a vaga
        return clienteVagaRepository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Recibo %s not found", recibo)
                )
        );
    }
}
