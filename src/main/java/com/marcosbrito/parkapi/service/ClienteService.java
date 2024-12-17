package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.exception.CpfUniqueViolationException;
import com.marcosbrito.parkapi.exception.EntityNotFoundException;
import com.marcosbrito.parkapi.repository.ClienteRepository;
import com.marcosbrito.parkapi.web.dto.ClienteResponseDto;
import com.marcosbrito.parkapi.web.dto.mapper.ClienteMapper;
import jakarta.transaction.TransactionScoped;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true) //Indica que é apenas para leitura
    public Cliente buscarPorId(@Valid Long id) {
        return repository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException(String.format("Client id ='%s' não encontrado", id))
        );}

    @Transactional(readOnly = true)
    public Page<Cliente> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
