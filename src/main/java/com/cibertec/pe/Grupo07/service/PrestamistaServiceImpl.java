package com.cibertec.pe.Grupo07.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.PrestamistaRepository;
@Service
public class PrestamistaServiceImpl implements PrestamistaService{

	@Autowired
	private PrestamistaRepository prestamistaRepository;
	@Override
	public List<Usuario> listarPrestamistasPorIdRol(Long idDelRol)  {
		// TODO Auto-generated method stub
		return prestamistaRepository.findAllByRolesId(idDelRol);
	}

	@Override
	public Usuario guardarPrestamista(Usuario usuario) {
		// TODO Auto-generated method stub
		return prestamistaRepository.save(usuario);
	}

	@Override
	public Usuario obtenerPrestamistaId(Long id) {
		Optional<Usuario> optionalUsuario = prestamistaRepository.findById(id);
        return optionalUsuario.orElse(null);
	}

	@Override
	public Usuario actualizarPrestamista(Usuario usuario) {
		return prestamistaRepository.save(usuario);
	}

	@Override
	public void eliminarPrestamista(Long id) {
		prestamistaRepository.deleteById(id);
		
	}
	@Override
	public List<Usuario> buscaPorDni(String dni) {
		// TODO Auto-generated method stub
		return prestamistaRepository.findByNumeroDocumentoIgnoreCase(dni);
	}

	@Override
	public List<Usuario> listarPrestamistasPorIdRolySede(Long idDelRol, Long idDeLaSede) {
		// TODO Auto-generated method stub
		return prestamistaRepository.findAllByRolesIdAndSedeId(idDelRol, idDeLaSede);
	}

}