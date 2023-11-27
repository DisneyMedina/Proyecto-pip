package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipoprestamo")
public class TipoPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoPrestamo")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;
    private Double tasa;
    private Integer estado;
}