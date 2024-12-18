package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<ClienteVaga, Long> {
    //Ele busca algo como o primeira vaga disponivel com o satatus LIVRE
    Optional<Vaga> findFirstByStatus(Vaga.StatusVaga statusVaga);
}
