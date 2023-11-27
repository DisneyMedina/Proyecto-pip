package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Data
@Table(name = "solicitud")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSolicitud")
    private Long id;

    private Double monto;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaInicioPrestamo;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fechaFinPrestamo;
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "idUsuarioPrestatario")
    private Usuario usuarioPrestatario;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro")
    private Usuario usuarioRegistro;
    @ManyToOne
    @JoinColumn(name = "idDetalleBancoUsuario")
    private BancoHasUsuario bancoHasUsuario;
    private Integer estado;
    
    @OneToOne(mappedBy = "solicitud")
    private Prestamo prestamo;
    
}