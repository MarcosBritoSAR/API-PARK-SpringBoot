package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.exception.EntityNotFoundException;
import com.marcosbrito.parkapi.repository.ClienteVagaRepository;
import com.marcosbrito.parkapi.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return clienteVagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }

    public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {

        return clienteVagaRepository.findAllByClienteCpf(cpf,pageable);

    }
}
