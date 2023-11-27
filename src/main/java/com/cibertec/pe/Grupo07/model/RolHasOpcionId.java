package com.cibertec.pe.Grupo07.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolHasOpcionId implements Serializable {
    private Long idRol;
    private Long idOpcion;

}
