package com.marcosbrito.parkapi.entity;

import jakarta.persistence.*;
import org.hibernate.graph.internal.AbstractGraphNode;

import javax.management.relation.Role;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(nullable = false, length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Role role;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    private String criadoPor;
    private String modificadoPor;


    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }

}
