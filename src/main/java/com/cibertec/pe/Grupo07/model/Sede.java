package com.cibertec.pe.Grupo07.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "sede")
public class Sede {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSede")
    private Long id;

    //private String iso;
    private String nombre;
    
    @OneToMany
    @JoinColumn(name ="idSede")
    private List<Usuario> usuarios = new ArrayList<>();
}
