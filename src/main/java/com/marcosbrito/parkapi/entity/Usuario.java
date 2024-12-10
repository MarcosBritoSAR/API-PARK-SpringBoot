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

    private String nome;
    private String password;


    private Role role;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    private String criadoPor;
    private String modificadoPor;


    public enum Role {
        ROLE_ADMIN, ROLE_CLIENT
    }

}
