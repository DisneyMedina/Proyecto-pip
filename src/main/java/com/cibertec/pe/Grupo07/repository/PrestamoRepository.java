package com.cibertec.pe.Grupo07.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.model.Solicitud;
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
	//List<Prestamo> findAll(Prestamo prestamo);
	@Query("SELECT p FROM Prestamo p " +
		       "WHERE (p.usuarioPrestatario.id = ?1 and p.estado =?2)")
		public List<Prestamo> listaPrestamoPorPrestatarioEstado(Long idUsuarioPrestatario, int estado);
}
