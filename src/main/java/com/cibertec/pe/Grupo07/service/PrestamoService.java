package com.cibertec.pe.Grupo07.service;

import java.util.List;

import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.model.Usuario;

public interface PrestamoService {
	public List<Prestamo> listarPrestamos();

	public Prestamo registrarPrestamo(Prestamo prestamo);

	public void eliminarPrestamo(Long idPrestamo);
	public Prestamo actualizaPrestamo(Prestamo prestamo);

	public Prestamo obtenerIdPrestamo(Long id);
	
	public List<Prestamo> listarPrestamoPorIdUsuarioPrestatarioEstado(Long usuarioPrestatario, int estado);
}