package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo")
    private Long id;

    private Double monto;

    @ManyToOne
    @JoinColumn(name = "idTipoPrestamo")
    private TipoPrestamo tipoPrestamo;

    @ManyToOne
    @JoinColumn(name = "idUsuarioPrestamista")
    private Usuario usuarioPrestamista;

    @ManyToOne
    @JoinColumn(name = "idUsuarioPrestatario")
    private Usuario usuarioPrestatario;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro")
    private Usuario usuarioRegistro;

    @ManyToOne
    @JoinColumn(name = "idUsuarioActualiza")
    private Usuario usuarioActualiza;

    private Date fechaRegistro;
    private Date fechaModificacion;
private int estado;
    @OneToOne
    @JoinColumn(name = "idSolicitud")
    private Solicitud solicitud;
}