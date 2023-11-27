package com.cibertec.pe.Grupo07.service;


import com.cibertec.pe.Grupo07.model.TipoPrestamo;
import com.cibertec.pe.Grupo07.repository.TipoPrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPrestamoServiceImpl implements  TipoPrestamoService {

    @Autowired
    private TipoPrestamoRepository tipoPrestamoRepository;


    @Override
    public List<TipoPrestamo> listaTipoPrestamos() {
        return tipoPrestamoRepository.findByOrderByDescripcionAsc();
    }
}
