package com.cibertec.pe.Grupo07.service;


import com.cibertec.pe.Grupo07.model.Banco;
import com.cibertec.pe.Grupo07.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoServiceImpl implements BancoService {

    @Autowired
    BancoRepository bancoRepository;

    @Override
    public List<Banco> listarBanco() {
        return bancoRepository.listarBanco();
    }
}
