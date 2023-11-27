package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "pais")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPais")
    private Long id;

    private String iso;
    private String nombre;
}