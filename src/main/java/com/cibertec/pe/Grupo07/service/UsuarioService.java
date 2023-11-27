package com.cibertec.pe.Grupo07.service;

import java.util.List;

import com.cibertec.pe.Grupo07.model.Opcion;
import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Usuario;

public interface UsuarioService {
    public List<Usuario> listarTodosLosUsuarios();

    public Usuario guardarUsuario(Usuario usuario);

    public Usuario obtenerUsuarioId(Long id);

    public Usuario actualizarUsuario(Usuario usuario);

    public void eliminarUsuario(Long id);
    
    public abstract Usuario login(Usuario bean);
    
    public abstract List<Usuario> buscaUsuarioPorJefePrestamistaEnSede(Long idJefePrestamista, Long idSede);
    
    public abstract List<Usuario> buscaUsuarioPorJefePrestamistaEnSedeSinRegistrar(Long idSede, Long id);
    
  //public List<Rol> rolesPorIdUsuario(Long id);
   // public abstract List<Rol> traerRolesDeUsuario(Long idUsuario);
    
    public abstract List<Usuario> buscaPorDni (String dni);
    public abstract List<Usuario> buscaPorEmail (String email);
    public abstract List<Usuario> buscaPorTelefono (String telefono);
    
    public abstract List<Usuario> buscaPorIdyDni (Long idUsuario, String dni);
    public abstract List<Usuario> buscaPorIdyEmail (Long idUsuario, String email);
    public abstract List<Usuario> buscaPorIdyTelefono (Long idUsuario, String telefono);
    public abstract List<Rol> traerRolesDeUsuario(Long idUsuario);
    public abstract List<Opcion> traerEnlacesDeUsuario(Long idUsuario);
    
    //Sprint 03 Emcontrar todos los prestamistas de un jefe de prestamista
    public abstract List<Usuario> traerPrestamistasDeSede(Long idSede);

}