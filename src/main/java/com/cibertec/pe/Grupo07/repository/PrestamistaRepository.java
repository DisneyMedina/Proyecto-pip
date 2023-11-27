package com.cibertec.pe.Grupo07.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Usuario;
@Repository
public interface PrestamistaRepository extends JpaRepository<Usuario, Long>{
	List<Usuario> findAllByRolesId(Long idDelRol);
	public abstract List<Usuario> findByNumeroDocumentoIgnoreCase(String dni);
	
	@Query("SELECT u FROM Rol r JOIN UsuarioHasRol ur ON r.id = ur.rol.id JOIN Usuario u " +
		       "ON ur.usuario.id = u.id " +
		       "WHERE r.id = ?1 AND u.sede.id = ?2")
	List<Usuario> findAllByRolesIdAndSedeId(Long idDelRol, Long idDeLaSede);
	
	
	
	
	
	
	
	
	
	
	
	//@Query("SELECT p FROM Prestamista p WHERE")
	
	
	
	
	
	
	
	
	
	
	
	
}