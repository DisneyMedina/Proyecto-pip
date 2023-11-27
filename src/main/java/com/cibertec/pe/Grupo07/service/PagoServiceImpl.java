package com.cibertec.pe.Grupo07.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Pago;
import com.cibertec.pe.Grupo07.repository.PagoRepository;
@Service
public class PagoServiceImpl implements PagoService {
	@Autowired
	private PagoRepository pagoRepository;
	@Override
	public List<Pago> listarPago() {
		// TODO Auto-generated method stub
		return pagoRepository.findAll();
	}

	@Override
	public Pago registrarPago(Pago pago) {
		// TODO Auto-generated method stub
		return pagoRepository.save(pago);
	}

	@Override
	public void eliminarPago(Long idPago) {
		pagoRepository.deleteById(idPago);
		
	}

	@Override
	public Pago actualizaPago(Pago pago) {
		// TODO Auto-generated method stub
		return pagoRepository.save(pago);
	}

	@Override
	public Pago obtenerIdCuota(Long id) {
		// TODO Auto-generated method stub
		Optional<Pago> optionalPago = pagoRepository.findById(id);
		return optionalPago.orElse(null);
	}

}
