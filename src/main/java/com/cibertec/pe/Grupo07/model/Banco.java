package com.cibertec.pe.Grupo07.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "banco")
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBanco")
    private Long id;

    private String nombre;
    private Integer estado;

    @OneToMany(mappedBy = "banco")
    @JsonIgnore
    private List<BancoHasUsuario> bancoHasUsuarios;
}
