package com.cibertec.pe.Grupo07.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Opcion;
import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioId(Long id) {
    	Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.orElse(null);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

	/*@Override
	public List<Rol> rolesPorIdUsuario(Long id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findRolesByIdUsuario(id);
	}*/

	@Override
	public Usuario login(Usuario bean) {
		return usuarioRepository.login(bean);
	}
/*
	@Override
	public List<Rol> traerRolesDeUsuario(Long idUsuario) {
		// TODO Auto-generated method stub
		return usuarioRepository.traerRolesDeUsuario(idUsuario);
	}*/

	@Override
	public List<Usuario> buscaUsuarioPorJefePrestamistaEnSede(Long idJefePrestamista, Long idSede) {
		// TODO Auto-generated method stub
		return usuarioRepository.findAllByJefePrestamistaEnSede(idJefePrestamista, idSede);
	}

	@Override
	public List<Usuario> buscaUsuarioPorJefePrestamistaEnSedeSinRegistrar(Long idSede,Long id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findAllByJefePrestamistaEnSedeAlRegistrar(idSede, 2L);
	}
	
	@Override
	public List<Usuario> buscaPorDni(String dni) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByNumeroDocumentoIgnoreCase(dni);
	}
	@Override
	public List<Usuario> buscaPorEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmailIgnoreCase(email);
	}
	@Override
	public List<Usuario> buscaPorTelefono(String telefono) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByTelefono(telefono);
	}
	@Override
	public List<Rol> traerRolesDeUsuario(Long idUsuario) {
		// TODO Auto-generated method stub
		return usuarioRepository.traerRolesDeUsuario(idUsuario);
	}

	@Override
	public List<Opcion> traerEnlacesDeUsuario(Long idUsuario) {
		// TODO Auto-generated method stub
		return usuarioRepository.traerEnlacesDeUsuario(idUsuario);
	}

	@Override
	public List<Usuario> buscaPorIdyDni(Long id, String dni) {
		// TODO Auto-generated method stub
		return usuarioRepository.busquedaIdyNumeroDocumento(id, dni);
	}

	@Override
	public List<Usuario> buscaPorIdyEmail(Long idUsuario, String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.busquedaIdyEmail(idUsuario, email);
	}

	@Override
	public List<Usuario> buscaPorIdyTelefono(Long idUsuario, String telefono) {
		// TODO Auto-generated method stub
		return usuarioRepository.busquedaIdyTelefono(idUsuario, telefono);
	}

	@Override
	public List<Usuario> traerPrestamistasDeSede(Long idSede) {
		// TODO Auto-generated method stub
		return usuarioRepository.findPrestamistasBySede(idSede);
	}

	
}