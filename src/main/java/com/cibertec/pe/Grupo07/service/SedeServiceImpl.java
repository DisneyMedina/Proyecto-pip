package com.cibertec.pe.Grupo07.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Sede;
import com.cibertec.pe.Grupo07.repository.SedeRepository;

@Service
public class SedeServiceImpl  implements SedeService {
	@Autowired
	private SedeRepository repository;
	@Override
	public List<Sede> listaSedes() {
		// TODO Auto-generated method stub
		return repository.findByOrderByNombreAsc();
	}

}
