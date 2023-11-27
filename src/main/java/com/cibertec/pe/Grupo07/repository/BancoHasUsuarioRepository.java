package com.cibertec.pe.Grupo07.repository;

import com.cibertec.pe.Grupo07.model.BancoHasUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BancoHasUsuarioRepository extends JpaRepository<BancoHasUsuario, Long> {

	@Query("SELECT b FROM BancoHasUsuario b WHERE b.estado = 1 and b.usuario.id = ?1")
  public abstract List<BancoHasUsuario> listarcuentaBanco(Long idUsuario);
	
	@Query("SELECT b FROM BancoHasUsuario b WHERE b.estado = 1 and b.cuenta = ?1")
	public abstract List<BancoHasUsuario> buscarPorNumeroDeCuenta(String cuenta);


}