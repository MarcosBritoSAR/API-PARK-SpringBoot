package com.marcosbrito.parkapi.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
