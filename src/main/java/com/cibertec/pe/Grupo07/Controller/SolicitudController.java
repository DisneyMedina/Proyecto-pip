package com.cibertec.pe.Grupo07.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.pe.Grupo07.model.Banco;
import com.cibertec.pe.Grupo07.model.BancoHasUsuario;
import com.cibertec.pe.Grupo07.model.Cuota;
import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.model.PrestamoCuotasRequest;
import com.cibertec.pe.Grupo07.model.Sede;
import com.cibertec.pe.Grupo07.model.Solicitud;
import com.cibertec.pe.Grupo07.model.TipoPrestamo;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.BancoHasUsuarioRepository;
import com.cibertec.pe.Grupo07.repository.BancoRepository;
import com.cibertec.pe.Grupo07.repository.SolicitudRepository;
import com.cibertec.pe.Grupo07.repository.TipoPrestamoRepository;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import com.cibertec.pe.Grupo07.service.BancoHasUsuarioService;
import com.cibertec.pe.Grupo07.service.CuotaService;
import com.cibertec.pe.Grupo07.service.PrestamoService;
import com.cibertec.pe.Grupo07.service.RolService;
import com.cibertec.pe.Grupo07.service.SolicitudService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.apachecommons.CommonsLog;

@Controller
@CommonsLog
@RequestMapping
public class SolicitudController {
	@Autowired
    private SolicitudService solicitudService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolService rolService;
    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private TipoPrestamoRepository tipoPrestamoRepository;

    @Autowired
    private CuotaService cuotaService;
    
    @Autowired
    private BancoRepository bancoRepository;
    
    @Autowired
    private BancoHasUsuarioService detalleBancoService;
    @Autowired
    private BancoHasUsuarioRepository bancoHasUsuarioRepository;
    
    @Autowired
    private SolicitudRepository solicitudRepository;
    @GetMapping("/solicitudes")
    public String listarSolicitud(
            Model model,
            @RequestParam(required = false, defaultValue = "1900-01-01") String desde,
            @RequestParam(required = false, defaultValue = "9999-01-01") String hasta,
            @RequestParam(required = false) String prestatario
    ) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
        long idSede = objUsuario.getSede().getId();
        // Permisos especiales
        boolean noEsPrestatario = userDetails.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_Administrador") ||
                                   authority.getAuthority().equals("ROLE_Prestamista") ||
                                   authority.getAuthority().equals("ROLE_JefePrestamista"));
        model.addAttribute("mostrarTextPrestatario", noEsPrestatario);
       if (noEsPrestatario) {
    	   Date fecDesdeDate = java.sql.Date.valueOf(desde);
           Date fecHastaDate = java.sql.Date.valueOf(hasta);
           if (prestatario ==null){
           	prestatario ="";
           }
           // Imprime los valores en la consola
           log.info(">>> " + "userDetails.getUsername(): " + userDetails.getUsername());
           log.info(">>> " + "idSede: " + idSede);
           log.info(">>> " + "prestatario: " + prestatario);
           log.info(">>> " + "fecDesdeDate: " + fecDesdeDate);
           log.info(">>> " + "fecHastaDate: " + fecHastaDate);
           
           List<Solicitud> solicitudes = solicitudService.listarSolicitudNombreApePaternoMaternoFechaInicioPrestamoSede("%" + prestatario + "%", fecDesdeDate, fecHastaDate, idSede);
           model.addAttribute("solicitudes", solicitudes);
           
           
       }
       else {
    	   
    	   List<Solicitud> solicitudes = solicitudService.listarSolicitudPorIdUsuarioPrestatario(objUsuario.getId());
           model.addAttribute("solicitudes", solicitudes);
       }
      
       
        return "solicitudes";
    }
    
    @GetMapping("/detalles/{idSolicitud}")
    public String mostrarDetallesSolicitud(@PathVariable Long idSolicitud, Model model) {
        // Busca la solicitud en el repositorio por su ID
        Solicitud solicitud = solicitudRepository.findById(idSolicitud).orElse(null);

        if (solicitud != null) {
            // Pasa los detalles de la solicitud al modelo
            model.addAttribute("solicitud", solicitud);
        }

        return "detalles-solicitud"; // Nombre de la vista donde se muestra el modal
    }

    @GetMapping("/solicitudes/nuevo")
    public String mostrarFormularioNuevoPrestamo(Model model) {
    	Solicitud solicitud = new Solicitud();
    	Date fechaActual = new Date();

    	// Crear un objeto Calendar y configurar la fecha actual
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(fechaActual);

    	// Agregar 2 días a la fecha actual
    	calendar.add(Calendar.DAY_OF_MONTH, 2);

    	// Obtener la fecha de inicio después de agregar 2 días
    	Date fechaInicio = calendar.getTime();

    	solicitud.setFechaInicioPrestamo(fechaInicio);

    	// Agregar la solicitud al modelo
    	model.addAttribute("solicitud", solicitud);
    	LocalDate fechaInicioDate = LocalDate.now(); // Configura la fecha de inicio como la fecha actual
        model.addAttribute("temporal", fechaInicioDate);
        
        
        List<Banco> listaBanco = bancoRepository.findAll();
        model.addAttribute("listaBanco", listaBanco);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
          
        
        List<BancoHasUsuario> listaDetalleBanco =bancoHasUsuarioRepository.listarcuentaBanco(objUsuario.getId());
        model.addAttribute("listaDetalleBanco",  listaDetalleBanco);
        
        
        return "crear_solicitud";
    }

    @PostMapping("/solicitudes")
    @ResponseBody
    public Map<?, ?> registrar(Solicitud solicitud, HttpSession httpSession, int detalleBancoUsuario) {

        
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
/*
            TipoPrestamo tipoPrestamo = new TipoPrestamo();
            tipoPrestamo.setId(1L);
           // tipoPrestamo.setDescripcion("efectivo");
*/
        Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);
        //solicitud.setMonto(solicitud.getMonto());
        //solicitud.setMonto(solicitud.getMonto());
        /*Usuario objPrestamista = new Usuario();
        objPrestamista.setId(-1L);*/
        solicitud.setUsuarioRegistro(objUsuario);
        solicitud.setFechaRegistro(new Date());
        solicitud.setEstado(1);
        /* AQUI SE PUEDE HACER QUE EL PRESTAMISTA REGISTRE AL PRESTATARIO*/
        solicitud.setUsuarioPrestatario(objUsuario);
        BancoHasUsuario detalleBanco = bancoHasUsuarioRepository.findById((long) detalleBancoUsuario).get();
        solicitud.setBancoHasUsuario(detalleBanco);
        HashMap<String, String> map = new HashMap<String, String>();
     // Modificación de fecha inicio
        
        Date fechaInicio = solicitud.getFechaInicioPrestamo();
        
     // Crear un objeto Calendar y configurar la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);

        // Agregar 1 día a la fecha actual
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Obtener la nueva fecha después de agregar 1 día
        Date nuevaFechaInicio = calendar.getTime();

        // Establecer la nueva fecha en la solicitud
        solicitud.setFechaInicioPrestamo(nuevaFechaInicio);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Lima")); // Establecer la zona horaria de Lima
        // Imprimir la nueva fecha de inicio
        log.info("Nueva fecha de inicio: " + dateFormat.format(nuevaFechaInicio));
      
     // Obtener la fecha actual
        Date fechaFin = solicitud.getFechaFinPrestamo();

        // Crear un objeto Calendar y configurar la fecha actual
        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fechaFin);

        // Agregar 1 día a la fecha actual
        calendarFin.add(Calendar.DAY_OF_MONTH, 1);

        // Obtener la nueva fecha después de agregar 1 día
        Date nuevaFechaFin = calendarFin.getTime();

        // Establecer la nueva fecha en la solicitud
        solicitud.setFechaFinPrestamo(nuevaFechaFin);
     // Imprimir la nueva fecha de fin
        log.info("Nueva fecha de fin: " + dateFormat.format(nuevaFechaFin));

        
        
      //Validaciones por idSolicitud y idPrestamo
        List<Solicitud> lstSalidaSolicitud = solicitudService.listarSolicitudPorIdUsuarioPrestatarioEstado(objUsuario.getId(), 1);
        List<Prestamo> lstSalidaPrestamo = prestamoService.listarPrestamoPorIdUsuarioPrestatarioEstado(objUsuario.getId(), 1);
		if (!CollectionUtils.isEmpty(lstSalidaSolicitud) || !CollectionUtils.isEmpty(lstSalidaPrestamo)) {
			map.put("MENSAJE", "Usted tiene una solicitud en Proceso o un préstamo en estado pagando");
			
			return map;
		}
        
        Solicitud objSalida = solicitudService.registrarSolicitud(solicitud);
        if (objSalida == null) {
			map.put("MENSAJE", "Error en el registro");
		} else {
			map.put("MENSAJE", "Registro exitoso");
		}
		return map;

    }
    @GetMapping("/solicitudes/editar/{id}")
    public String editSolicitudForm(@PathVariable Long id, Model model) {
        Solicitud solicitud = solicitudService.obtenerIdSolicitud(id);
        model.addAttribute("solicitud", solicitud);
        // Puedes cargar las opciones para las relaciones aquí si es necesario.
        return "editar_solicitud";
    }
    @PostMapping("/solicitud/editar/{id}")
    public String editSolicitud(@PathVariable Long id, @ModelAttribute Solicitud solicitud) {
    	solicitudService.actualizaSolicitud(solicitud);
        return "redirect:/solicitudes";
    }

    @GetMapping("/solicitud/delete/{id}")
    public String deleteSolicitud(@PathVariable Long id) {
    	solicitudService.eliminarSolicitud(id);
        return "redirect:/solicitudes";
    }
    
    @PostMapping("/registrar_prestamo_y_cuotas")
    @ResponseBody
    public Map<?, ?> registraregistrarPrestamoYCuotas(@RequestBody PrestamoCuotasRequest request) throws ParseException{
       
          
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String username = userDetails.getUsername();
 /*
             TipoPrestamo tipoPrestamo = new TipoPrestamo();
             tipoPrestamo.setId(1L);
            // tipoPrestamo.setDescripcion("efectivo");
 */
         Solicitud solicitudSalida = null;
         Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);
    	Solicitud solicitud = solicitudService.obtenerIdSolicitud(request.getIdPrestamo());
    	HashMap<String, String> map = new HashMap<String, String>();
    	if (request.getEstadoSolicitud() == 2) {
    		solicitud.setEstado(2);
        	//HashMap<String, String> map = new HashMap<String, String>();
        	solicitudSalida = solicitudService.actualizaSolicitud(solicitud);
        	Usuario objPrestatario = (Usuario) usuarioRepository.getById((Long) request.getIdUsuarioPrestario());
        	Prestamo prestamo = new Prestamo();
        	prestamo.setFechaRegistro(new Date());
        	prestamo.setFechaModificacion(new Date());
        	prestamo.setMonto(request.getMonto());
        	TipoPrestamo tipoPrestamo =  tipoPrestamoRepository.getById( 1);
        	
        	
        	prestamo.setTipoPrestamo(tipoPrestamo);
        
        	prestamo.setUsuarioPrestamista(objUsuario);
        	prestamo.setUsuarioPrestatario(objPrestatario);
        	prestamo.setUsuarioRegistro(objUsuario);
        	prestamo.setUsuarioActualiza(objUsuario);
        	prestamo.setSolicitud(solicitudSalida);
        	prestamo.setEstado(1);
        	Prestamo prestamoSalida = prestamoService.actualizaPrestamo(prestamo);
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        	// Crear cuotas asociadas al préstamo
            int cantidadCuotas = request.getCantidadCuotas();
            double cuota = request.getMontoCuota();
            Date fechaInicio =dateFormat.parse( request.getFechaInicio());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicio);
            for (int i = 1; i <= cantidadCuotas; i++) {
                Cuota cuotanew = new Cuota();
                cuotanew.setNumero(i);
                cuotanew.setMonto(cuota);
                
                cuotanew.setPrestamo(prestamoSalida);
                cuotanew.setUsuarioRegistro(objUsuario);
                cuotanew.setFechaRegistro(new Date());
                cuotanew.setEstado(1);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date fechaFin = calendar.getTime();
                cuotanew.setFechaPago(fechaFin);
                cuotaService.registrarCuota(cuotanew);
            }
    	} else if (request.getEstadoSolicitud() == 0){
    		solicitud.setEstado(0);
        	//HashMap<String, String> map = new HashMap<String, String>();
        	solicitudSalida = solicitudService.actualizaSolicitud(solicitud);
    	}
    	
    
    	solicitudSalida = solicitudService.actualizaSolicitud(solicitud);
    	
    	if (solicitudSalida == null) {
			map.put("MENSAJE", "Error en la actualización");
		} else {
			map.put("MENSAJE", "Actualización Existosa");
		}
		return map;
            /*
            
            
            prestamo.setFechaInicio(request.getFechaInicio());
            prestamo.setFechaFin(request.getFechaFin());

            prestamo = prestamoService.registrarPrestamo(prestamo);

            // Crear cuotas asociadas al préstamo
            int cantidadCuotas = request.getCantidadCuotas();
            double cuota = request.getCuota();
            
            for (int i = 1; i <= cantidadCuotas; i++) {
                Cuota cuota = new Cuota();
                cuota.setNumero(i);
                cuota.setMonto(cuota);
                cuota.setPrestamo(prestamo);
                
                cuotaService.registrarCuota(cuota);*/
            
    }
    @PostMapping("/solicitudes/crear/numeroCuenta")
    @ResponseBody
    public Map<?, ?> registrarnumeroCuenta(int banco, String cuenta) {

    	log.info(">>> " + banco);
    	log.info(">>> " + cuenta);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);
        BancoHasUsuario detalleBanco = new BancoHasUsuario();
        detalleBanco.setUsuario(objUsuario);
        Banco bancoSeleccionado = bancoRepository.findById((long) banco).get();
        log.info(">>> " + bancoSeleccionado.getNombre());
        detalleBanco.setBanco(bancoSeleccionado);
        detalleBanco.setCuenta(cuenta);
        detalleBanco.setEstado(1);
        
        
        HashMap<String, String> map = new HashMap<String, String>();

        BancoHasUsuario objSalida = detalleBancoService.guardarDetalleBanco(detalleBanco);
        if (objSalida == null) {
			map.put("MENSAJE", "Error en el registro");
		} else {
			map.put("MENSAJE", "Registro exitoso");
		}
		return map;

    }
    
    @GetMapping("/listaDetalleBancoUsuario2")
    @ResponseBody
    public List<BancoHasUsuario> obtenerListaDetalleBancoUsuario() {
        // Lógica para obtener la lista de detalles de banco de usuario
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);
        // Supongamos que tienes un servicio que proporciona esta lista
        List<BancoHasUsuario> listaDetalleBancoUsuario = bancoHasUsuarioRepository.listarcuentaBanco(objUsuario.getId());

        return listaDetalleBancoUsuario;
    }
}