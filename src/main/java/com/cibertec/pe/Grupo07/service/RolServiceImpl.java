package com.cibertec.pe.Grupo07.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{
	private RolRepository rolRepository;
	@Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
	@Override
	public Rol obtenerRolId(Long id) {
		Rol rol = rolRepository.findById(id).orElse(null);
        return rol;
	}

}