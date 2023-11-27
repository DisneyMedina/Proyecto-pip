package com.cibertec.pe.Grupo07.Controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import com.cibertec.pe.Grupo07.service.PrestamistaService;
import com.cibertec.pe.Grupo07.service.RolService;
import com.cibertec.pe.Grupo07.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping()
public class PrestamistaController {
	@Autowired
	private PrestamistaService servicio;
	@Autowired
	private UsuarioService servicioUsuario;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private RolService servicioRol;
	
	@GetMapping({"/prestamistas"})
	public String listarPrestamistas(Model modelo) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		// Permisos especiales
		boolean esAdministrador = userDetails.getAuthorities().stream()
	            .anyMatch(authority -> authority.getAuthority().equals("ROLE_Administrador"));
		modelo.addAttribute("mostrarSedeNombre", esAdministrador);
        String username = userDetails.getUsername();
		Usuario objUsuario = (Usuario) repository.findUserByEmail(username);
		
		Long sede = objUsuario.getSede().getId();
		
	    
	    Long roleId = 0L;
		for (Rol rol : objUsuario.getRoles()) {
	        // Aquí, puedes acceder al ID del rol y hacer lo que necesites con él
	        roleId = rol.getId();
		}
		List<Usuario> listaPrestamistas = servicio.listarPrestamistasPorIdRolySede(3L, sede);
		if (roleId == 2L) {
			listaPrestamistas = servicio.listarPrestamistasPorIdRolySede(3L, sede);
		} else if (roleId == 1L) {
			listaPrestamistas = servicio.listarPrestamistasPorIdRol(3L);
		} 
	    modelo.addAttribute("listaPrestamistas", listaPrestamistas);
	    return "prestamistas";
	}
	
	@GetMapping("/prestamistas/nuevo")
    public String mostrarFormularioNuevoPrestamista(Model modelo) {
		
        Usuario prestamista = new Usuario();
        //modelo.addAttribute("listaRoles",listaRoles);
        
        modelo.addAttribute("prestamista", prestamista);
        return "prestamistas";
    }
	
	
	@PostMapping("/prestamistas")
	@ResponseBody
	public Map<?, ?> registra(Usuario prestamista, HttpSession session){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        
		Usuario objUsuario = (Usuario) repository.findUserByEmail(username);
		System.out.println("ID del usuario: " + objUsuario.getId());
		prestamista.setSede(objUsuario.getSede());
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passEncryp = bCryptPasswordEncoder.encode(prestamista.getPassword());
        prestamista.setPassword(passEncryp);
		Rol listaRoles = servicioRol.obtenerRolId(3L);
        prestamista.setFlag(0);
        prestamista.setEstado((short) 1);
        prestamista.setFechaRegistro(new Date());
        prestamista.addRol(listaRoles);
        prestamista.setUsuarioRegistro(objUsuario);
        prestamista.setUsuarioModificacion(objUsuario);
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		Usuario objSalida = servicio.guardarPrestamista(prestamista);
		if (objSalida == null) {
			map.put("MENSAJE", "Error en el registro");
		}else {
			map.put("MENSAJE", "Registro exitoso");
		}
		return map;
	}
	
	
	@GetMapping("/prestamistas/buscaUsuarioPorDNI")
	@ResponseBody
	public String validaDNI(String numeroDocumento){
		List<Usuario> lstLibro = servicio.buscaPorDni(numeroDocumento);
		if(CollectionUtils.isEmpty(lstLibro)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	

	@GetMapping("/prestamistas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("prestamista", servicio.obtenerPrestamistaId(id));
        return "editar_prestamista";
    }

    @PostMapping("/prestamistas/editar/{id}")
    @ResponseBody
    public Map<?, ?> actualizarUsuario(@PathVariable Long id,Usuario prestamista, HttpSession session){
    
    	System.out.println("ID del usuario: " + id);
 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Accede a los detalles del usuario
        String username = userDetails.getUsername();
		Usuario objUsuario = (Usuario) repository.findUserByEmail(username);
		System.out.println("ID del usuario: " + objUsuario.getId());
		prestamista.setSede(objUsuario.getSede());
        Usuario actual = servicio.obtenerPrestamistaId(id);
        Rol listaRoles = servicioRol.obtenerRolId(3L);
        prestamista.addRol(listaRoles);
        prestamista.setFechaRegistro(actual.getFechaRegistro());
        prestamista.setFechaModificacion(new Date());
        prestamista.setUsuarioRegistro(actual.getUsuarioRegistro());
        prestamista.setUsuarioModificacion(objUsuario);
        prestamista.setFlag(0);
        prestamista.setEstado((short) 1);
     // Verifica si la contraseña en el formulario es igual a la contraseña
     		// almacenada en la base de datos
		if (!prestamista.getPassword().equals(actual.getPassword())) {
			// La contraseña en el formulario ha cambiado, encripta la nueva contraseña
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String passEncryp = bCryptPasswordEncoder.encode(actual.getPassword());
			prestamista.setPassword(passEncryp);
		} else {
			// La contraseña en el formulario es igual a la contraseña original, no la
			// encriptes nuevamente
			prestamista.setPassword(actual.getPassword());
		}

		HashMap<String, String> map = new HashMap<String, String>();
		Usuario objSalida = servicio.actualizarPrestamista(prestamista);
		if (objSalida == null) {
			map.put("MENSAJE", "Error en la actualización");
		}else {
			map.put("MENSAJE", "Actualización Existosa");
		}
		return map;
    }

    @GetMapping("/prestamistas/{id}")
    public String eliminarPrestamista(@PathVariable Long id) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Accede a los detalles del usuario
		String username = userDetails.getUsername();
		Usuario objUsuario = (Usuario) repository.findUserByEmail(username);
		Usuario actual = servicio.obtenerPrestamistaId(id);
		//actual.setFechaRegistro(actual.getFechaRegistro());
		actual.setUsuarioModificacion(objUsuario);
		actual.setFechaModificacion(new Date());
		actual.setEstado(actual.getEstado() == 1 ? (short)0 : (short)1);
        servicio.actualizarPrestamista(actual);
        return "redirect:/prestamistas";
    }
}