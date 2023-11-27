package com.cibertec.pe.Grupo07.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rol_has_opcion")
public class RolHasOpcion {
    @EmbeddedId
    private RolHasOpcionId id;

    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol", insertable = false, updatable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idOpcion", referencedColumnName = "idOpcion", insertable = false, updatable = false)
    private Opcion opcion;
}