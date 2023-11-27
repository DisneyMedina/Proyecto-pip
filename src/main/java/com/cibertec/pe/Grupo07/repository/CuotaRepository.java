package com.cibertec.pe.Grupo07.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Cuota;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
	
	@Query("SELECT c FROM Cuota c " +
		       "WHERE  ( -1=?1 or  c.prestamo.usuarioPrestatario.id=?1) and " +
		       "DATE_FORMAT(c.fechaPago, '%Y-%m-%d') >= ?2 AND " +
		       "DATE_FORMAT(c.fechaPago, '%Y-%m-%d') <= ?3")
		public List<Cuota> listaCuotaPorPrestatarioDesdeHastaFechaVencimiento(Long idUsuarioPrestatario, Date desde, Date hasta);
	
	/*@Query("SELECT c.prestamo.id,c.id, CONCAT(c.prestamo.usuarioPrestatario.nombres, ' ', c.prestamo.usuarioPrestatario.paterno, ' ', c.prestamo.usuarioPrestatario.materno), c.numero, c.monto, c.fechaPago, c.estado, MAX(p.fechaRegistro) AS fechaUltimoPago, c.monto - COALESCE(SUM(p.monto), 0) AS deuda " +
		       "FROM Cuota c " +
		       "LEFT JOIN Pago p ON c.id = p.cuota.id " +
		       "WHERE c.prestamo.usuarioPrestatario.sede.id = ?4 AND (CONCAT(c.prestamo.usuarioPrestatario.nombres, ' ', c.prestamo.usuarioPrestatario.paterno, ' ', c.prestamo.usuarioPrestatario.materno) LIKE ?1 OR c.prestamo.usuarioPrestatario.nombres LIKE ?1 or c.prestamo.usuarioPrestatario.paterno LIKE ?1 OR c.prestamo.usuarioPrestatario.materno LIKE ?1) AND " +
		       "FUNCTION('DATE_FORMAT', c.fechaPago, '%Y-%m-%d') >= ?2 AND " +
		       "FUNCTION('DATE_FORMAT', c.fechaPago, '%Y-%m-%d') <= ?3 " +
		       "GROUP BY c.id, c.numero, c.monto, c.fechaPago")
		public List<Object[]> findCuotaInfoWithDeudaAndUltimoPago(String nombreUsuario, Date desde, Date hasta, Long idSede);*/
	
	@Query("SELECT c.prestamo.id,c.id, CONCAT(c.prestamo.usuarioPrestatario.nombres, ' ', c.prestamo.usuarioPrestatario.paterno, ' ', c.prestamo.usuarioPrestatario.materno), c.numero, c.monto, c.fechaPago, c.estado, MAX(p.fechaRegistro) AS fechaUltimoPago, c.monto - COALESCE(SUM(p.monto), 0) AS deuda " +
		       "FROM Cuota c " +
		       "LEFT JOIN Pago p ON c.id = p.cuota.id " +
		       "WHERE (-1=?4 or c.prestamo.usuarioPrestatario.id = ?4 ) AND " + 
		       "(-1=?5 or c.prestamo.usuarioPrestamista.id = ?5 ) AND" +
		       "(CONCAT(c.prestamo.usuarioPrestatario.nombres, ' ', c.prestamo.usuarioPrestatario.paterno, ' ', c.prestamo.usuarioPrestatario.materno) LIKE ?1 OR c.prestamo.usuarioPrestatario.nombres LIKE ?1 or c.prestamo.usuarioPrestatario.paterno LIKE ?1 OR c.prestamo.usuarioPrestatario.materno LIKE ?1) AND " +
		       "FUNCTION('DATE_FORMAT', c.fechaPago, '%Y-%m-%d') >= ?2 AND " +
		       "FUNCTION('DATE_FORMAT', c.fechaPago, '%Y-%m-%d') <= ?3 " +
		       "GROUP BY c.id, c.numero, c.monto, c.fechaPago")
		public List<Object[]> findCuotaInfoWithDeudaAndUltimoPago(String nombreUsuario, Date desde, Date hasta, Long idPrestatario, Long idPrestamista);
		
		
		@Query("SELECT p.id, p.monto, CONCAT(c.prestamo.usuarioPrestatario.nombres, ' ', c.prestamo.usuarioPrestatario.paterno, ' ', c.prestamo.usuarioPrestatario.materno) AS nombreCompleto, " +
		           "SUM(CASE WHEN c.estado = 0 THEN 1 ELSE 0 END) AS cantidadCuotasEstado2, " +
		           "SUM(CASE WHEN c.estado = 1 THEN 1 ELSE 0 END) AS cantidadCuotasEstado1, " +
		           "SUM(CASE WHEN c.estado = 0 THEN c.monto ELSE 0 END) AS sumaMontoCuotasEstado2, " +
		           "SUM(CASE WHEN c.estado = 1 THEN c.monto ELSE 0 END) AS sumaMontoCuotasEstado1 " +
		           "FROM Prestamo p " +
		           "LEFT JOIN Cuota c ON p.id = c.prestamo.id " +
		           "WHERE (p.usuarioPrestamista.id =?1 or -1=?1) and p.usuarioPrestamista.sede.id=?2 " +
				"GROUP BY p.id, p.monto, nombreCompleto")
		    List<Object[]> obtenerResumenPrestamo( Long usuarioPrestamistaId, Long idSede);
		    
		    
		    @Query("SELECT COUNT(c) FROM Cuota c WHERE c.prestamo.id =?1")
		    int obtenerNumeroCuotas(Long idPrestamo);
		    
		    
		    
		    
}
