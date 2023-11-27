package com.cibertec.pe.Grupo07.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.pe.Grupo07.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService{

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Override
	public List<Prestamo> listarPrestamos() {
		// TODO Auto-generated method stub
		return prestamoRepository.findAll();
	}

	@Override
	public Prestamo registrarPrestamo(Prestamo prestamo) {
		// TODO Auto-generated method stub
		return prestamoRepository.save(prestamo);
	}

	@Override
	public void eliminarPrestamo(Long idPrestamo) {
		   prestamoRepository.deleteById(idPrestamo);
	}

	@Override
	public Prestamo actualizaPrestamo(Prestamo prestamo) {
		return prestamoRepository.save(prestamo);
	}

	@Override
	public Prestamo obtenerIdPrestamo(Long id) {
		Optional<Prestamo> optionalPrestamo = prestamoRepository.findById(id);
		return optionalPrestamo.orElse(null);
	}

	@Override
	public List<Prestamo> listarPrestamoPorIdUsuarioPrestatarioEstado(Long usuarioPrestatario, int estado) {
		// TODO Auto-generated method stub
		return prestamoRepository.listaPrestamoPorPrestatarioEstado(usuarioPrestatario, estado);
	}


}