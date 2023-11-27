package com.cibertec.pe.Grupo07.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Solicitud;
import com.cibertec.pe.Grupo07.repository.SolicitudRepository;

@Service
public class SolicitudServiceImpl implements SolicitudService {

	@Autowired
	private SolicitudRepository SolicitudRepository;

	@Override
	public List<Solicitud> listarSolicitud() {
		// TODO Auto-generated method stub
		return SolicitudRepository.findAll();
	}

	@Override
	public Solicitud registrarSolicitud(Solicitud Solicitud) {
		// TODO Auto-generated method stub
		return SolicitudRepository.save(Solicitud);
	}

	@Override
	public void eliminarSolicitud(Long idSolicitud) {
		SolicitudRepository.deleteById(idSolicitud);
	}

	@Override
	public Solicitud actualizaSolicitud(Solicitud Solicitud) {
		return SolicitudRepository.save(Solicitud);
	}

	@Override
	public Solicitud obtenerIdSolicitud(Long id) {
		Optional<Solicitud> optionalSolicitud = SolicitudRepository.findById(id);
		return optionalSolicitud.orElse(null);
	}

	//Consulta
	@Override
	public List<Solicitud> listarSolicitudNombreApePaternoMaternoFechaInicioPrestamo(String nomApe, Date desde,
			Date hasta) {
		// TODO Auto-generated method stub
		return SolicitudRepository.listaConsultaSolicitud(nomApe, desde, hasta);
	}

	@Override
	public List<Solicitud> listarSolicitudPorIdUsuarioPrestatario(Long usuarioPrestatario) {
		// TODO Auto-generated method stub
		return SolicitudRepository.listaConsultaSolicitudPorPrestatario(usuarioPrestatario);
	}

	@Override
	public List<Solicitud> listarSolicitudPorIdUsuarioPrestatarioEstado(Long usuarioPrestatario, int estado) {
		// TODO Auto-generated method stub
		return SolicitudRepository.listaSolicitudPorPrestatarioEstado(usuarioPrestatario, estado);
	}

	@Override
	public List<Solicitud> listarSolicitudNombreApePaternoMaternoFechaInicioPrestamoSede(String nomApe, Date desde,
			Date hasta, Long idSede) {
		// TODO Auto-generated method stub
		return SolicitudRepository.listaConsultaSolicitudSede(nomApe, desde, hasta,idSede);
	}

}