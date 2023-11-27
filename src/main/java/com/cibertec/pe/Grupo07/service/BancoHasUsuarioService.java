package com.cibertec.pe.Grupo07.service;

import java.util.List;

import com.cibertec.pe.Grupo07.model.BancoHasUsuario;

public interface BancoHasUsuarioService {
   public abstract List<BancoHasUsuario> listarcuentaBanco(String cuenta);
	public BancoHasUsuario guardarDetalleBanco(BancoHasUsuario detalleBanco);

	
}
