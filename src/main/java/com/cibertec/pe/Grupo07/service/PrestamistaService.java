package com.cibertec.pe.Grupo07.service;

import java.util.List;

import com.cibertec.pe.Grupo07.model.Usuario;

public interface PrestamistaService {
	public List<Usuario> listarPrestamistasPorIdRol(Long idDelRol);

    public Usuario guardarPrestamista(Usuario usuario);

    public Usuario obtenerPrestamistaId(Long id);

    public Usuario actualizarPrestamista(Usuario usuario);

    public void eliminarPrestamista(Long id);
    public abstract List<Usuario> buscaPorDni (String dni);
    public List<Usuario> listarPrestamistasPorIdRolySede(Long idDelRol, Long idDeLaSede);

}