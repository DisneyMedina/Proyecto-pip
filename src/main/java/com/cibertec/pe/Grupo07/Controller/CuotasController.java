package com.cibertec.pe.Grupo07.Controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.pe.Grupo07.model.Cuota;
import com.cibertec.pe.Grupo07.model.Pago;
import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.CuotaRepository;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import com.cibertec.pe.Grupo07.service.CuotaService;
import com.cibertec.pe.Grupo07.service.PagoService;
import com.cibertec.pe.Grupo07.service.PrestamoService;

import lombok.extern.apachecommons.CommonsLog;

@Controller
@CommonsLog
@RequestMapping
public class CuotasController {
	@Autowired
	private CuotaService cuotaService;
	@Autowired
	private CuotaRepository cuotaRepositorio;
	
	@Autowired
	private PrestamoService prestamoService;
	@Autowired
	private PagoService pagoService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@GetMapping("/cuotas")
	public String listarCuotas(Model model, @RequestParam(required = false, defaultValue = "1900-01-01") String desde,
            @RequestParam(required = false, defaultValue = "9999-01-01") String hasta, @RequestParam(required = false, defaultValue = "") String prestatario)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		//Usuario en el sistema
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
		boolean noEsPrestatario = userDetails.getAuthorities().stream()
	            .anyMatch(authority -> authority.getAuthority().equals("ROLE_Administrador") ||
	                                   authority.getAuthority().equals("ROLE_Prestamista") ||
	                                   authority.getAuthority().equals("ROLE_JefePrestamista"));
	        model.addAttribute("mostrarTextPrestatario", noEsPrestatario);
		Date fecDesdeDate = java.sql.Date.valueOf(desde);
		Date fecHastaDate = java.sql.Date.valueOf(hasta);
		List<Cuota> cuotas = cuotaService.listarCuotaPorPrestatarioDesdeHastaFechaVencimiento((long)-1,fecDesdeDate, fecHastaDate);
		List<Map<String, Object>> cuotaInfo = cuotaService.getCuotaInfoConDeudaYUltimoPago("%",fecDesdeDate, fecHastaDate, (long) -1, (long) -1);
		if (noEsPrestatario) {
			cuotaInfo = cuotaService.getCuotaInfoConDeudaYUltimoPago("%" + prestatario+ "%",fecDesdeDate, fecHastaDate,(long) -1, objUsuario.getId());
		}
		else {
			cuotaInfo = cuotaService.getCuotaInfoConDeudaYUltimoPago("%",fecDesdeDate, fecHastaDate, objUsuario.getId(), (long) -1);
		}
		
		model.addAttribute("cuotas", cuotaInfo);
		 // Imprime los valores en la consola
        //log.info(">>> " + "prestatario: " + prestatario);
        log.info(">>> " + "fecDesdeDate: " + fecDesdeDate);
        log.info(">>> " + "fecHastaDate: " + fecHastaDate);
		return "cuotas";
	}
	@GetMapping("/resumen")
	public String listarCuotasPrestamista(Model model,@RequestParam(required = false,  defaultValue = "-1") String prestamista)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		//Usuario en el sistema
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
		boolean noEsPrestatario = userDetails.getAuthorities().stream()
	            .anyMatch(authority -> authority.getAuthority().equals("ROLE_Administrador") ||
	                                   authority.getAuthority().equals("ROLE_Prestamista") ||
	                                   authority.getAuthority().equals("ROLE_JefePrestamista"));
	        model.addAttribute("mostrarTextPrestatario", noEsPrestatario);
	       int idPrestamista = Integer.parseInt(prestamista);
	        List<Map<String, Object>> resumenes = cuotaService.getResumenPrestamo((long) idPrestamista, objUsuario.getSede().getId());
	        
	        log.info(">>> " + " idPrestamista: " +  prestamista);
	    List<Usuario> listaPrestamistasSede = usuarioRepository.findPrestamistasBySede(objUsuario.getSede().getId());
		model.addAttribute("resumenes", resumenes);
		model.addAttribute("listaPrestamistas",listaPrestamistasSede);
       
        
		return "resumenPrestamo";
	}
	
	
	@PostMapping("/pagar")
	@ResponseBody
	public Map<?, ?> realizarPago(@RequestParam Long idCuota, @RequestParam Double monto, @RequestParam Double mora,@RequestParam Double deuda, @RequestParam int numeroCuota) {
	    // Crear un objeto Pago y establecer los valores recibidos
	    Pago pago = new Pago();
	    Cuota cuota = cuotaService.obtenerIdCuota(idCuota);
	    
	    BigDecimal montoDecimal = BigDecimal.valueOf(monto);
	    BigDecimal deudaDecimal = BigDecimal.valueOf(deuda);
	    double montoReal = 0;
	    if (monto >= mora) {
	    	montoReal = monto - mora;
	    }
	    if (montoDecimal.compareTo(deudaDecimal) == 0) {
	    	cuota.setEstado(0);
	    }
	    else {
	    	cuota.setEstado(1);
	    }
	    Cuota objCuota =  cuotaService.actualizaCuota(cuota);
	    
	    int numFinalCuotaPrestamo = cuotaRepositorio.obtenerNumeroCuotas(objCuota.getPrestamo().getId());
	    Prestamo prestamo = prestamoService.obtenerIdPrestamo(objCuota.getPrestamo().getId());
	    if (numeroCuota == numFinalCuotaPrestamo) {
	    	prestamo.setEstado(2);
	    	
	    }
	    else {
	    	prestamo.setEstado(2);
	    }
	    Prestamo objPrestamo = prestamoService.actualizaPrestamo(prestamo);

	    pago.setCuota(objCuota); // Ajusta según tu modelo
	    pago.setMonto(montoReal);
	    pago.setMora(mora);
	    pago.setEstado(1);
	    
	    HashMap<String, String> map = new HashMap<String, String>();
	    
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
	    // Lógica para guardar el pago en la base de datos
	    // Ejemplo: pagoService.registrarPago(pago);
		pago.setUsuarioRegistro(objUsuario);
		pago.setFechaRegistro(new Date());
		Pago objSalida = pagoService.registrarPago(pago);
		if (objSalida == null) {
			map.put("MENSAJE", "Error en el registro");
		} else {
			map.put("MENSAJE", "Registro exitoso");
		}
		return map;
		 // Redirige a la página de cuotas después de realizar el pago.
	}
	
	
	
	
	
	@GetMapping("/validarMontoPago")
	@ResponseBody
	public String validaJefePrestamistaSede(String montoPago, String mora, String deuda) {
		Double montoDouble = Double.parseDouble(montoPago);
		Double moraDouble = Double.parseDouble(mora);
		Double deudaDouble = Double.parseDouble(deuda);
		if (montoDouble > moraDouble && montoDouble> 0.0 && montoDouble<=deudaDouble) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	
	}

	
}
