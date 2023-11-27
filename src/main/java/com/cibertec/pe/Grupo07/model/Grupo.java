package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "grupo")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGrupo")
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idUbigeo")
    private Ubigeo ubigeo;

    private Integer estado;
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "idUsuarioLider")
    private Usuario usuarioLider;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro")
    private Usuario usuarioRegistro;
}
