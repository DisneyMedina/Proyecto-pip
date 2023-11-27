package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "formapago")
public class FormaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFormaPago")
    private Long id;

    private String descripcion;
    private Integer estado;
}
