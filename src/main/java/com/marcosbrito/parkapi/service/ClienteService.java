package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.exception.CpfUniqueViolationException;
import com.marcosbrito.parkapi.exception.EntityNotFoundException;
import com.marcosbrito.parkapi.repository.ClienteRepository;
import com.marcosbrito.parkapi.repository.projection.ClienteProjection;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(Cliente cliente) {
        try {
            return repository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' nao pode ser cadastrad no sistema. Ja existe um cpf cadastrado", cliente.getCpf())) ;
        }
    }

    @Transactional(readOnly = true) //Indica que é apenas para leitura
    public Cliente buscarPorId(@Valid Long id) {
        return repository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException(String.format("Client id ='%s' não encontrado", id))
        );}

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return repository.findAllPageable(pageable);
    }


    @Transactional(readOnly = true)
    public Cliente buscarUsuarioPorId(Long idDoUsuario) {
    return repository.findByUsuarioId(idDoUsuario);
    }
@Transactional(readOnly = true)
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com o cpf = '%s' nao encontrado", cpf))
        );
    }
}
