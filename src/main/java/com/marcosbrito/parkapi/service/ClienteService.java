package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.exception.CpfUniqueViolationException;
import com.marcosbrito.parkapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente) {
        try {
            return repository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' nao pode ser cadastrad no sistema. Ja existe um cpf cadastrado", cliente.getCpf())) ;
        }
    }

}
