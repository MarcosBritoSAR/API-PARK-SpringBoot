package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<ClienteVaga, Long> {
}
