package com.cibertec.pe.Grupo07.model;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Entity
@Data
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole nombre;
    private Short estado;
	public Rol(ERole nombre) {
		super();
		this.nombre = nombre;
		this.estado = 1;
	}
	public Rol() {
		super();
	}
	public Rol(Long id) {
		super();
		this.id = id;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
    
    
}