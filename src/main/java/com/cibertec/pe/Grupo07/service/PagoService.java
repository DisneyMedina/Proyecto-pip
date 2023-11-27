package com.cibertec.pe.Grupo07.service;

import java.util.List;

import com.cibertec.pe.Grupo07.model.Pago;

public interface PagoService {
	public List<Pago> listarPago();

	public Pago registrarPago(Pago pago);

	public void eliminarPago(Long idPago);

	public Pago actualizaPago(Pago pago);

	public Pago obtenerIdCuota(Long id);

}
