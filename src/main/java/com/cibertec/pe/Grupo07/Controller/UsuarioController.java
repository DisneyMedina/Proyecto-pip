package com.cibertec.pe.Grupo07.Controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Sede;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.RolRepository;
import com.cibertec.pe.Grupo07.repository.SedeRepository;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import com.cibertec.pe.Grupo07.service.PrestamistaService;
import com.cibertec.pe.Grupo07.service.UsuarioService;
import com.cibertec.pe.Grupo07.util.FunctionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.extern.apachecommons.CommonsLog;
@CommonsLog
@Controller
@RequestMapping()
public class UsuarioController {

	private final PasswordEncoder passwordEncoder;

	public UsuarioController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	private UsuarioService servicio;
	@Autowired
	private PrestamistaService servicioPrestamista;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private SedeRepository sedeRepository;

	@GetMapping("/usuarios")
	public String listarUsuarios(Model modelo) {
		modelo.addAttribute("usuarios", servicio.listarTodosLosUsuarios());
		
		Usuario usuario = new Usuario();

		List<Rol> listaRoles = rolRepository.findAll();
		modelo.addAttribute("listaRoles", listaRoles);

		modelo.addAttribute("usuario", usuario);
		List<Sede> listaSede = sedeRepository.findAll();
		modelo.addAttribute("listaSede", listaSede);
		
		return "usuarios";
	}

	@GetMapping("/usuarios/nuevo")

	public String mostrarFormularioNuevoUsuario(Model modelo) {
		Usuario usuario = new Usuario();

		List<Rol> listaRoles = rolRepository.findAll();
		modelo.addAttribute("listaRoles", listaRoles);

		modelo.addAttribute("usuario", usuario);
		
		
		
		List<Sede> listaSede = sedeRepository.findAll();
		modelo.addAttribute("listaSede", listaSede);
		return "usuarios";
	}

	@PostMapping("/usuarios")
	@ResponseBody
	public Map<?, ?> registra(Usuario usuario) {

		usuario.setFlag(0);
		usuario.setEstado((short) 1);
		usuario.setFechaRegistro(new Date());
		usuario.setFechaModificacion(new Date());
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
		usuario.setUsuarioRegistro(objUsuario);
		usuario.setUsuarioModificacion(objUsuario);
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String passEncryp = bCryptPasswordEncoder.encode(usuario.getPassword());
		usuario.setPassword(passEncryp);

		HashMap<String, String> map = new HashMap<String, String>();
		Long roleId = 0L;
		for (Rol rol : usuario.getRoles()) {
	        // Aquí, puedes acceder al ID del rol y hacer lo que necesites con él
	        roleId = rol.getId();
		}
		
		if (roleId == 2L) {
			List<Usuario> lstUsuario = servicio.buscaUsuarioPorJefePrestamistaEnSedeSinRegistrar(usuario.getSede().getId(),2L);
			if (!CollectionUtils.isEmpty(lstUsuario)) {
				Usuario objFind = lstUsuario.get(0);
				map.put("MENSAJE", "Ya existe un Jefe de prestamista" + " en la sede " + objFind.getSede().getNombre());
				return map;
			}
		}
		
		Usuario objSalida = servicio.guardarUsuario(usuario);
		if (objSalida == null) {
			map.put("MENSAJE", "Error en el registro");
		} else {
			map.put("MENSAJE", "Registro exitoso");
			map.put("tableUpdate", "true"); 
		}
		return map;
	}

	@GetMapping("/usuarios/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model modelo) {
		List<Rol> listaRoles = rolRepository.findAll();
		modelo.addAttribute("listaRoles", listaRoles);
		Usuario usuario = usuarioRepository.findById(id).get();
		String contrasena = usuario.getPassword();
		modelo.addAttribute("contrasena", contrasena);
		modelo.addAttribute("usuario", servicio.obtenerUsuarioId(id));
		List<Sede> listaSede = sedeRepository.findAll();
		modelo.addAttribute("listaSede", listaSede);
		return "editar_usuario";
	}

	@PostMapping("/usuarios/editar/{id}")
	@ResponseBody
	public Map<?, ?> actualizarUsuario(@PathVariable Long id, Usuario usuario, HttpSession session) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Accede a los detalles del usuario
		String username = userDetails.getUsername();
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);

		Usuario actual = servicio.obtenerUsuarioId(id);
		usuario.setFechaRegistro(actual.getFechaRegistro());
		usuario.setUsuarioModificacion(objUsuario);
		usuario.setUsuarioRegistro(actual.getUsuarioRegistro());
		usuario.setFlag(0);
		usuario.setFechaModificacion(new Date());
		usuario.setEstado(actual.getEstado());
		usuario.setUsuarioRegistro(actual.getUsuarioRegistro());
		usuario.setUsuarioModificacion(objUsuario);
		// Verifica si la contraseña en el formulario es igual a la contraseña
		// almacenada en la base de datos
		if (!usuario.getPassword().equals(actual.getPassword())) {
			// La contraseña en el formulario ha cambiado, encripta la nueva contraseña
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String passEncryp = bCryptPasswordEncoder.encode(usuario.getPassword());
			usuario.setPassword(passEncryp);
		} else {
			// La contraseña en el formulario es igual a la contraseña original, no la
			// encriptes nuevamente
			usuario.setPassword(actual.getPassword());
		}

		HashMap<String, String> map = new HashMap<String, String>();
		
		Long roleId = 0L;
		for (Rol rol : usuario.getRoles()) {
	        // Aquí, puedes acceder al ID del rol y hacer lo que necesites con él
	        roleId = rol.getId();
		}
		
		if (roleId == 2L) {
			List<Usuario> lstUsuario = servicio.buscaUsuarioPorJefePrestamistaEnSede(id,usuario.getSede().getId());
			if (!CollectionUtils.isEmpty(lstUsuario)) {
				Usuario objFind = lstUsuario.get(0);
				map.put("MENSAJE", "Ya existe un Jefe de prestamista" + " en la sede " + objFind.getSede().getNombre());
				return map;
			}
		}
		/*
		List<Usuario> lstUsuario2 = servicio.buscaPorIdyDni(id,usuario.getNumeroDocumento());
		if (!CollectionUtils.isEmpty(lstUsuario2)) {
			Usuario objFind = lstUsuario2.get(0);
			map.put("MENSAJE", "Ya se ha registrado el Dni" + objFind.getSede().getNombre());
			return map;
		
		}
		*/
		Usuario objSalida = servicio.actualizarUsuario(usuario);
		if (objSalida == null) {
			map.put("MENSAJE", "Error en la actualización");
		} else {
			map.put("MENSAJE", "Actualización Existosa");
		}
		return map;
	}

	@GetMapping("/usuarios/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Accede a los detalles del usuario
		String username = userDetails.getUsername();
		Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);

		Usuario actual = servicio.obtenerUsuarioId(id);
		//actual.setFechaRegistro(actual.getFechaRegistro());
		actual.setUsuarioModificacion(objUsuario);
		//actual.setUsuarioRegistro(actual.getUsuarioRegistro());
		//actual.setFlag(0);
		// Verifica si existen usuarios relacionados al jefe de prestamista en la sede
		Long roleId = 0L;
		for (Rol rol : actual.getRoles()) {
	        // Aquí, puedes acceder al ID del rol y hacer lo que necesites con él
	        roleId = rol.getId();
		}
		
		if (roleId == 2L) {
			List<Usuario> usuariosRelacionados = servicio.buscaUsuarioPorJefePrestamistaEnSede(actual.getId(), actual.getSede().getId());
			if (!usuariosRelacionados.isEmpty()) {
	            model.addAttribute("error", "No se puede eliminar este usuario porque tiene usuarios relacionados.");
	            return "redirect:/usuarios";// Redirige a una página de error personalizada con el mensaje de error.
			}
		}
		actual.setFechaModificacion(new Date());
		actual.setEstado(actual.getEstado() == 1 ? (short)0 : (short)1);
		servicio.actualizarUsuario(actual);
		return "redirect:/usuarios";
	}

	@GetMapping("/usuarios/buscaUsuarioPorJefePrestamistaEnSedeSinRegistrar")
	@ResponseBody
	public String ValidaUsuarioPorJefePrestamistaEnSede(Long idSede) {
		List<Usuario> lstUsuario = servicio.buscaUsuarioPorJefePrestamistaEnSedeSinRegistrar(idSede, 3L);
		if (CollectionUtils.isEmpty(lstUsuario)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	// Validacion de email único
	@GetMapping("/buscaUsuarioPorEmail")
	@ResponseBody
	public String validaEmail(String email) {
		List<Usuario> lstUsurio = servicio.buscaPorEmail(email);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	// Validacion de DNI único
	@GetMapping("/buscaUsuarioPorDNI")
	@ResponseBody
	public String validaDNI(String numeroDocumento) {
		List<Usuario> lstUsurio = servicio.buscaPorDni(numeroDocumento);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	// Validacion de DNI único
	@GetMapping("/buscaUsuarioPorTelefono")
	@ResponseBody
	public String validaTelefono(String telefono) {
		List<Usuario> lstUsurio = servicio.buscaPorTelefono(telefono);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	

	@GetMapping("/buscaUsuarioMayorEdad")
	@ResponseBody
	public String validaFecha(String fechaNacimiento) {
		log.info(">> validaFecha >> " + fechaNacimiento);
		if(FunctionUtil.isMayorEdad(fechaNacimiento)) {
			return "{\"valid\":true}";
		}else {
			return "{\"valid\":false}";
		}
	}
	@GetMapping("/buscaUsuarioPorDNIyIdUsuario")
	@ResponseBody
	public String validaDNIConIdUsuario(String numeroDocumento, String id) {
		Long idLong = Long.parseLong(id);
		List<Usuario> lstUsurio = servicio.buscaPorIdyDni(idLong, numeroDocumento);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	@GetMapping("/buscaUsuarioPorEmailyIdUsuario")
	@ResponseBody
	public String validaEmailConIdUsuario(String email, String id) {
		Long idLong = Long.parseLong(id);
		List<Usuario> lstUsurio = servicio.buscaPorIdyEmail(idLong, email);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	@GetMapping("/buscaUsuarioPorTelefonoyIdUsuario")
	@ResponseBody
	public String validaTelefonoConIdUsuario(String telefono, String id) {
		Long idLong = Long.parseLong(id);
		List<Usuario> lstUsurio = servicio.buscaPorIdyTelefono(idLong, telefono);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
	}
	
	@GetMapping("/buscaJefePrestamistaSedeConIdUsuario")
	@ResponseBody
	public String validaJefePrestamistaSede(String sede, String rol, String id) {
		Long idSedeLong = Long.parseLong(sede);
		Long idRolLong = Long.parseLong(rol);
		Long idUsuarioLong = Long.parseLong(id);
		if (idRolLong == 2L) {
		List<Usuario> lstUsurio = servicio.buscaUsuarioPorJefePrestamistaEnSede(idUsuarioLong, idSedeLong);
		if (CollectionUtils.isEmpty(lstUsurio)) {
			return "{\"valid\" : true }";
		} else {
			return "{\"valid\" : false }";
		}
		}
		return "{\"valid\" : true }";
	}
	/*
	@GetMapping("/userInfo")
    public String getUserInfo(Authentication authentication) {
        // Obtenemos la información de autenticación actual
        if (authentication != null) {
            // Accedemos a los roles del usuario autenticado
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            // Iteramos a través de los roles y los imprimimos
            for (GrantedAuthority authority : authorities) {
                System.out.println("Rol del usuario: " + authority.getAuthority());
            }
            
            // Puedes hacer cualquier otra lógica basada en roles aquí
            // Por ejemplo, redirigir al usuario según su rol, mostrar contenido específico, etc.
            
            return "Información del usuario obtenida con éxito";
        } else {
            return "Usuario no autenticado";
        }
    }*/

}
