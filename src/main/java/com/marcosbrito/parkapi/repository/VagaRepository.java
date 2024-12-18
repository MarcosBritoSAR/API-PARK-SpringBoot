package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    //Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(Vaga.StatusVaga statusVaga);
}
