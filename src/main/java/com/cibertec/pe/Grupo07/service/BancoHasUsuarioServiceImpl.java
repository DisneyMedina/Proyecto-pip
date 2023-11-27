package com.cibertec.pe.Grupo07.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.BancoHasUsuario;
import com.cibertec.pe.Grupo07.repository.BancoHasUsuarioRepository;
@Service
public class BancoHasUsuarioServiceImpl implements BancoHasUsuarioService {

    @Autowired
    private BancoHasUsuarioRepository bancoHasUsuarioRepository;

	@Override
	public BancoHasUsuario guardarDetalleBanco(BancoHasUsuario detalleBanco) {
		// TODO Auto-generated method stub
		return bancoHasUsuarioRepository.save(detalleBanco);
	}

	@Override
	public List<BancoHasUsuario> listarcuentaBanco(String cuenta) {
		// TODO Auto-generated method stub
		return bancoHasUsuarioRepository.buscarPorNumeroDeCuenta(cuenta);
	}

   /* @Override
    public List<BancoHasUsuario> listarcuentaBanco(Long idUsuario) {
        return bancoHasUsuarioRepository.listarcuentaBanco(idUsuario);
    }*/
}