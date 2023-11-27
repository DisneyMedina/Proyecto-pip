package com.cibertec.pe.Grupo07.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.pe.Grupo07.model.Cuota;
import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.repository.CuotaRepository;

@Service
public class CuotaServiceImpl implements CuotaService {

	@Autowired
	private CuotaRepository cuotaRepository;

	@Override
	public List<Cuota> listarCuota() {
		// TODO Auto-generated method stub
		return cuotaRepository.findAll();
	}

	@Override
	public Cuota registrarCuota(Cuota cuota) {
		// TODO Auto-generated method stub
		return cuotaRepository.save(cuota);
	}

	@Override
	public void eliminarCuota(Long idCuota) {
		// TODO Auto-generated method stub
		cuotaRepository.deleteById(idCuota);

	}

	@Override
	public Cuota actualizaCuota(Cuota cuota) {
		// TODO Auto-generated method stub
		return cuotaRepository.save(cuota);
	}

	@Override
	public Cuota obtenerIdCuota(Long id) {
		// TODO Auto-generated method stub
		Optional<Cuota> optionalCuota = cuotaRepository.findById(id);
		return optionalCuota.orElse(null);
	}

	@Override
	public List<Cuota> listarCuotaPorPrestatarioDesdeHastaFechaVencimiento(Long idUsuarioPrestatario, Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return cuotaRepository.listaCuotaPorPrestatarioDesdeHastaFechaVencimiento(idUsuarioPrestatario, desde, hasta);
	}
/*
	@Override
	public List<Map<String, Object>> getCuotaInfoConDeudaYUltimoPago(String nombreUsuario, Date desde, Date hasta, Long idSede) {
		List<Object[]> cuotaInfo = cuotaRepository.findCuotaInfoWithDeudaAndUltimoPago(nombreUsuario, desde, hasta, idSede);
	    List<Map<String, Object>> cuotaInfoList = new ArrayList<>();

	    for (Object[] cuota : cuotaInfo) {
	        Long idPrestamo = (Long) cuota[0];
	        Long idCuota = (Long) cuota[1];
	        String nombreUsuarioString = (String) cuota[2];
	        Integer numeroCuota = (Integer) cuota[3];
	        Double montoCuota = (Double) cuota[4];
	        Date fechaVencimiento = (Date) cuota[5];
	        Integer estadoCuota = (Integer) cuota[6];
	        Date fechaUltimoPago = (Date) cuota[7];
	        Double deuda = (Double) cuota[8];
	     // Tasa diaria, ajusta según tus necesidades
	        double tasaDiaria = Math.pow((1 + 0.2), 1.0 / 30) - 1;
	        Double mora = 0.0;
	        if (estadoCuota == 1 && deuda != 0 && fechaVencimiento != null) {
	            Date fechaActual = new Date();
	            long diferenciaDias;

	            if (fechaUltimoPago != null) {
	                // Calcular la mora con la nueva fórmula basada en la tasa diaria
	                diferenciaDias = calcularDiferenciaDias(fechaUltimoPago, fechaActual);
	                mora = montoCuota * (Math.pow((1 + tasaDiaria), diferenciaDias) - 1);
	            } else {
	                // Calcular la mora con la nueva fórmula basada en la tasa diaria
	                diferenciaDias = calcularDiferenciaDias(fechaVencimiento, fechaActual);
	                mora = Math.round(montoCuota * (Math.pow((1 + tasaDiaria), diferenciaDias) - 1) * 100.0) / 100.0;
	            }
	        }
	        Map<String, Object> cuotaInfoMap = new HashMap<>();
	        cuotaInfoMap.put("idPrestamo", idPrestamo);
	        cuotaInfoMap.put("idCuota", idCuota);
	        cuotaInfoMap.put("nombreUsuario", nombreUsuarioString);
	        cuotaInfoMap.put("numeroCuota", numeroCuota);
	        cuotaInfoMap.put("montoCuota", montoCuota);
	        cuotaInfoMap.put("fechaVencimiento", fechaVencimiento);
	        cuotaInfoMap.put("estadoCuota", estadoCuota);
	        cuotaInfoMap.put("fechaUltimoPago", fechaUltimoPago);
	        cuotaInfoMap.put("deuda", deuda);
	        cuotaInfoMap.put("mora", mora);
	        cuotaInfoMap.put("sumaDeudaMora", deuda + mora);

	        cuotaInfoList.add(cuotaInfoMap);
	    }

	    return cuotaInfoList;
	}
	*/
	@Override
	public List<Map<String, Object>> getCuotaInfoConDeudaYUltimoPago(String nombreUsuario, Date desde, Date hasta, Long idPrestatario, Long idPrestamista) {
		List<Object[]> cuotaInfo = cuotaRepository.findCuotaInfoWithDeudaAndUltimoPago(nombreUsuario, desde, hasta, idPrestatario, idPrestamista);
	    List<Map<String, Object>> cuotaInfoList = new ArrayList<>();

	    for (Object[] cuota : cuotaInfo) {
	        Long idPrestamo = (Long) cuota[0];
	        Long idCuota = (Long) cuota[1];
	        String nombreUsuarioString = (String) cuota[2];
	        Integer numeroCuota = (Integer) cuota[3];
	        Double montoCuota = (Double) cuota[4];
	        Date fechaVencimiento = (Date) cuota[5];
	        Integer estadoCuota = (Integer) cuota[6];
	        Date fechaUltimoPago = (Date) cuota[7];
	        Double deuda = (Double) cuota[8];
	     // Tasa diaria, ajusta según tus necesidades
	        double tasaDiaria = Math.pow((1 + 0.2), 1.0 / 30) - 1;
	        Double mora = 0.0;
	        if (estadoCuota == 1 && deuda != 0 && fechaVencimiento != null) {
	            Date fechaActual = new Date();
	            long diferenciaDias;

	            if (fechaUltimoPago != null) {
	                // Calcular la mora con la nueva fórmula basada en la tasa diaria
	                diferenciaDias = calcularDiferenciaDias(fechaUltimoPago, fechaActual);
	               
	                mora = montoCuota * (Math.pow((1 + tasaDiaria),diferenciaDias) - 1);
	                mora = diferenciaDias <= 0 ? 0 : mora;
	            } else {
	                // Calcular la mora con la nueva fórmula basada en la tasa diaria
	                diferenciaDias = calcularDiferenciaDias(fechaVencimiento, fechaActual);
	                
	                mora = Math.round(montoCuota * (Math.pow((1 + tasaDiaria), diferenciaDias) - 1) * 100.0) / 100.0;
	                mora = diferenciaDias <= 0 ? 0 : mora;
	            }
	        }
	        
	        BigDecimal deudaDecimal = BigDecimal.valueOf(deuda);
	        deudaDecimal = deudaDecimal.setScale(2, RoundingMode.HALF_UP);
	        
	     // Redondear a dos decimales
	        BigDecimal moraDecimal = BigDecimal.valueOf(mora);
	        moraDecimal = moraDecimal.setScale(2, RoundingMode.HALF_UP);
	        
	        
	        double sumadeuda = mora + deuda;
	        BigDecimal sumadeudaDecimal= BigDecimal.valueOf(sumadeuda);
	        sumadeudaDecimal= sumadeudaDecimal.setScale(2, RoundingMode.HALF_UP);
	        Map<String, Object> cuotaInfoMap = new HashMap<>();
	        cuotaInfoMap.put("idPrestamo", idPrestamo);
	        cuotaInfoMap.put("idCuota", idCuota);
	        cuotaInfoMap.put("nombreUsuario", nombreUsuarioString);
	        cuotaInfoMap.put("numeroCuota", numeroCuota);
	        cuotaInfoMap.put("montoCuota", montoCuota);
	        cuotaInfoMap.put("fechaVencimiento", fechaVencimiento);
	        cuotaInfoMap.put("estadoCuota", estadoCuota);
	        cuotaInfoMap.put("fechaUltimoPago", fechaUltimoPago);
	        cuotaInfoMap.put("deuda", deudaDecimal);
	        cuotaInfoMap.put("mora", moraDecimal);
	        cuotaInfoMap.put("sumaDeudaMora", sumadeudaDecimal);

	        cuotaInfoList.add(cuotaInfoMap);
	    }

	    return cuotaInfoList;
	}
	
	
	private long calcularDiferenciaDias(Date fechaInicial, Date fechaFinal) {
        long diferenciaMillis = fechaFinal.getTime() - fechaInicial.getTime();
        return diferenciaMillis / (24 * 60 * 60 * 1000);
    }

	@Override
	public List<Map<String, Object>> getResumenPrestamo(Long usuarioPrestamistaId, Long idSede) {
		// TODO Auto-generated method stub
		
		
		
		List<Object[]> resumenPrestamo =  cuotaRepository.obtenerResumenPrestamo(usuarioPrestamistaId, idSede);
		
		//List<Object[]> cuotaInfo = cuotaRepository.findCuotaInfoWithDeudaAndUltimoPago(nombreUsuario, desde, hasta, idPrestatario, idPrestamista);
	    List<Map<String, Object>> resumenPrestamoList = new ArrayList<>();

	    for (Object[] resumen : resumenPrestamo) {
	        Long idPrestamo = (Long) resumen[0];
	        double montoPrestamo = (double) resumen[1];
	        String nombrePrestatario = (String) resumen[2];
	        long cuotasPagadas = (long) resumen[3];
	        long cuotasNoPagadas = (long) resumen[4];
	        double montocuotasPagadas = (double) resumen[5];
	        double montocuotasNoPagadas = (double) resumen[6];
	        
	       
	        Map<String, Object> resumenPrestamoMap = new HashMap<>();
	        resumenPrestamoMap.put("idPrestamo", idPrestamo);
	        resumenPrestamoMap.put("montoPrestamo",montoPrestamo);
	        resumenPrestamoMap.put("nombrePrestatario",nombrePrestatario);
	        resumenPrestamoMap.put("cuotasPagadas", cuotasPagadas);
	        resumenPrestamoMap.put("cuotasNoPagadas", cuotasNoPagadas);
	        resumenPrestamoMap.put("montocuotasPagadas", montocuotasPagadas);
	        resumenPrestamoMap.put("montocuotasNoPagadas", montocuotasNoPagadas);
	      

	        resumenPrestamoList.add(resumenPrestamoMap);
	    }

	    return resumenPrestamoList;
	}

}
