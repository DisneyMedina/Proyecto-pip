package com.cibertec.pe.Grupo07.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cibertec.pe.Grupo07.model.Cuota;

public interface CuotaService {
	public List<Cuota> listarCuota();

	public Cuota registrarCuota(Cuota cuota);

	public void eliminarCuota(Long idCuota);

	public Cuota actualizaCuota(Cuota cuota);

	public Cuota obtenerIdCuota(Long id);
	
	public List<Cuota> listarCuotaPorPrestatarioDesdeHastaFechaVencimiento(Long idUsuarioPrestatario, Date desde, Date hasta);
	public List<Map<String, Object>>  getCuotaInfoConDeudaYUltimoPago(String nombreUsuario, Date desde, Date hasta, Long idPrestatario, Long idPrestamista);
	public List<Map<String, Object>>  getResumenPrestamo(Long usuarioPrestamistaId, Long idSede);
}
