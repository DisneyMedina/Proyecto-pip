package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ubigeo")
public class Ubigeo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUbigeo")
    private Long id;

    private String departamento;
    private String provincia;
    private String distrito;
}