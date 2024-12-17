package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
}
