package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPago")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCuota")
    private Cuota cuota;

    private Double monto;
    private Double mora;
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro")
    private Usuario usuarioRegistro;

    @ManyToOne
    @JoinColumn(name = "idFormaPago")
    private FormaPago formaPago;

    private Integer estado;
}