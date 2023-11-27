package com.cibertec.pe.Grupo07.service;

import java.util.Date;
import java.util.List;

import com.cibertec.pe.Grupo07.model.Solicitud;

public interface SolicitudService {
	public List<Solicitud> listarSolicitud();

	public Solicitud registrarSolicitud(Solicitud prestamo);

	public void eliminarSolicitud(Long idPrestamo);
	public Solicitud actualizaSolicitud(Solicitud prestamo);

	public Solicitud obtenerIdSolicitud(Long id);
	
	public List<Solicitud> listarSolicitudNombreApePaternoMaternoFechaInicioPrestamo(String nomApe, Date desde, Date hasta);
	public List<Solicitud> listarSolicitudPorIdUsuarioPrestatario(Long usuarioPrestatario);
	
	public List<Solicitud> listarSolicitudPorIdUsuarioPrestatarioEstado(Long usuarioPrestatario, int estado);
	
	public List<Solicitud> listarSolicitudNombreApePaternoMaternoFechaInicioPrestamoSede(String nomApe, Date desde, Date hasta, Long idSede);

}
