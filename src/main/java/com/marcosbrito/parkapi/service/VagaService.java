package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.Vaga;
import com.marcosbrito.parkapi.exception.EntityNotFoundException;
import com.marcosbrito.parkapi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.marcosbrito.parkapi.entity.Vaga.StatusVaga.LIVRE;

@Service
@RequiredArgsConstructor
public class VagaService {
    private final VagaRepository vagaRepository;

    public Vaga buscarVagaLivre() {
        //O jpa transforma a assinatura do metodo em uma Query
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("Nenhum vaga livre encontrado")
        );
    }
}
