package com.cibertec.pe.Grupo07.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
	@Query("SELECT s FROM Solicitud s " +
		       "WHERE (s.usuarioPrestatario.nombres like ?1 or s.usuarioPrestatario.paterno like ?1 or s.usuarioPrestatario.materno like ?1 ) and " +      
		       "s.fechaInicioPrestamo >= ?2 and " +
		       "s.fechaInicioPrestamo <= ?3")
		public List<Solicitud> listaConsultaSolicitud(String nomApe,
		                                              Date desde,
		                                              Date hasta);
	@Query("SELECT s FROM Solicitud s " +
		       "WHERE (s.usuarioPrestatario.id = ?1 )")
		public List<Solicitud> listaConsultaSolicitudPorPrestatario(Long idUsuarioPrestatario);
	
	
	@Query("SELECT s FROM Solicitud s " +
		       "WHERE (s.usuarioPrestatario.id = ?1 and s.estado =?2)")
		public List<Solicitud> listaSolicitudPorPrestatarioEstado(Long idUsuarioPrestatario, int estado);
	
	
	@Query("SELECT s FROM Solicitud s " +
		       "WHERE s.usuarioPrestatario.sede.id=?4 and (s.usuarioPrestatario.nombres like ?1 or s.usuarioPrestatario.paterno like ?1 or s.usuarioPrestatario.materno like ?1 ) and " +      
		       "s.fechaInicioPrestamo >= ?2 and " +
		       "s.fechaInicioPrestamo <= ?3")
		public List<Solicitud> listaConsultaSolicitudSede(String nomApe,
		                                              Date desde,
		                                              Date hasta, Long idSede);
	
	@Query("SELECT s FROM Solicitud s " +
		       "WHERE s.usuarioPrestatario.sede.id=?4 and (s.usuarioPrestatario.nombres like ?1 or s.usuarioPrestatario.paterno like ?1 or s.usuarioPrestatario.materno like ?1 ) and " +      
		       "s.fechaInicioPrestamo >= ?2 and " +
		       "s.fechaInicioPrestamo <= ?3")
		public List<Solicitud> listaConsultaSolicitudSedeRegistrado(String nomApe,
		                                              Date desde,
		                                              Date hasta, Long idSede);
}
