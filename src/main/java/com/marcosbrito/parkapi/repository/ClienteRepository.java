package com.marcosbrito.parkapi.repository;

import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);
}
