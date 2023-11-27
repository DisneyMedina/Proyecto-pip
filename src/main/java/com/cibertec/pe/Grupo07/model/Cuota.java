package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "cuota")
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCuota")
    private Long id;

    private Integer numero;
    private Double monto;
    private Date fechaPago;
    private Date fechaRegistro;
    private Integer estado;

    @ManyToOne
    @JoinColumn(name = "idPrestamo")
    private Prestamo prestamo;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro")
    private Usuario usuarioRegistro;
    
    
}

