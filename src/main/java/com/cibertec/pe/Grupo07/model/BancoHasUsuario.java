package com.cibertec.pe.Grupo07.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

@Table(name = "banco_has_usuario")
public class BancoHasUsuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleBancoUsuario")
    private Long id;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBanco", referencedColumnName = "idBanco")
	@JsonIgnore
    private Banco banco;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
	@JsonIgnore
    private Usuario usuario;

    private String cuenta;
    private Integer estado;
}
