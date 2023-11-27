package com.cibertec.pe.Grupo07.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PrestamoCuotasRequest {

    private Long idPrestamo;
    private Double monto;
    private String fechaInicio;
    private Long idUsuarioPrestario;
    private String fechaFin;
    private Integer cantidadCuotas;
    private double montoCuota;
    private Integer estadoSolicitud;

    // Getters y setters
}
