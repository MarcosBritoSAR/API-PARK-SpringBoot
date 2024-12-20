package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.entity.Vaga;
import com.marcosbrito.parkapi.exception.CodigoUniqueViolationException;
import com.marcosbrito.parkapi.exception.EntityNotFoundException;
import com.marcosbrito.parkapi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.marcosbrito.parkapi.entity.Vaga.StatusVaga.LIVRE;

@Service
@RequiredArgsConstructor
public class VagaService {
    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException("Vaga", vaga.getCodigo());
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Vaga", codigo)
        );
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("vaga","")
        );
    }
}
