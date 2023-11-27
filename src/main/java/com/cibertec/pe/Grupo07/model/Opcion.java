package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "opcion")
public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOpcion")
    private Long id;

    private String nombre;
    private String ruta;
    private String estado;
    private Short tipo;
}