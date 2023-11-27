package com.cibertec.pe.Grupo07.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.pe.Grupo07.model.Opcion;
import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>   {
	
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario findUserByEmail(@Param("email") String email);
    
    //List<Rol> findRolesByIdUsuario(Long idUsuario);
    
    @Query("Select x from Usuario x where x.email = :#{#usu.email} and x.password = :#{#usu.password}")
	public abstract Usuario login(@Param(value = "usu") Usuario bean);
    /*
    @Query("Select r from Rol r, UsuarioHasRol u where r.idRol = u.rol.id and u.usuario.id = :var_idUsuario")
	public abstract List<Rol> traerRolesDeUsuario(@Param("var_idUsuario")Long idUsuario);*/
    @Query("SELECT u FROM UsuarioHasRol ur JOIN Usuario u " +
		       "ON ur.usuario.id = u.id " +
		       "WHERE ur.rol.id = 2 AND u.id != ?1 AND u.sede.id = ?2 AND u.estado = 1")
	List<Usuario> findAllByJefePrestamistaEnSede(Long idUsuarioPrestamista, Long idDeLaSede);
    @Query("SELECT u FROM UsuarioHasRol ur JOIN Usuario u " +
		       "ON ur.usuario.id = u.id " +
		       "WHERE ur.rol.id =?2 AND u.sede.id = ?1 AND u.estado = 1")
	List<Usuario> findAllByJefePrestamistaEnSedeAlRegistrar(Long idDeLaSede, Long id);
    
    public abstract List<Usuario> findByNumeroDocumentoIgnoreCase(String dni);
    public abstract List<Usuario> findByEmailIgnoreCase(String email);
    public abstract List<Usuario> findByTelefono(String telefono);
    @Query("SELECT u FROM Usuario u " +
		       "WHERE u.id != ?1 AND u.numeroDocumento = ?2")
    public abstract List<Usuario> busquedaIdyNumeroDocumento(Long idUsuario, String dni);
    @Query("SELECT u FROM Usuario u " +
		       "WHERE u.id != ?1 AND u.email = ?2")
    public abstract List<Usuario> busquedaIdyEmail(Long idUsuario, String email);
    @Query("SELECT u FROM Usuario u " +
		       "WHERE u.id != ?1 AND u.telefono = ?2")
    public abstract List<Usuario> busquedaIdyTelefono(Long idUsuario, String telefono);
    // Momentaneo
    Optional<Usuario> findByEmail(String email);
    
    @Query("Select r from Rol r, UsuarioHasRol u where r.id = u.rol.id and u.usuario.id = :var_idUsuario")
	public abstract List<Rol> traerRolesDeUsuario(@Param("var_idUsuario")Long idUsuario);
    
    @Query("Select p from Opcion p, RolHasOpcion pr, Rol r, UsuarioHasRol u where  p.id = pr.opcion.id and pr.rol.id = r.id and r.id = u.rol.id and u.usuario.id = :var_idUsuario")
	public abstract List<Opcion> traerEnlacesDeUsuario(@Param("var_idUsuario") Long idUsuario);
    
    
    @Query("SELECT u FROM Usuario u " +
            "JOIN u.roles r " +
            "JOIN u.sede s " +
            "WHERE r.id = 3 AND s.id =?1")
     List<Usuario> findPrestamistasBySede(Long sedeId);
    
    
    
    
}