package com.cibertec.pe.Grupo07.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.pe.Grupo07.model.Opcion;
import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class HomeController {
	@Autowired
	private UsuarioService servicio;
	// @ResponseBody
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	

	@ResponseBody
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return "about";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);
		return "redirect:/login";
	}
	/*
	 * @GetMapping("/login-success") public String loginSuccess(HttpSession session)
	 * { // Obtén el objeto de autenticación actual Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * // Guarda el objeto de autenticación en la sesión
	 * session.setAttribute("userAuthentication", authentication);
	 * 
	 * return "redirect:/usuarios"; // Redirige a la página de inicio o dashboard }
	 */
	@RequestMapping("/")
	public String paginaPrincipal() {
		return "paginaprincipal";

	}
	
	@RequestMapping("/indexAdmin")
	public String index(Authentication auth, HttpSession session) {
		String nomUsuario=auth.getName();
		Usuario bean=servicio.buscaPorEmail(nomUsuario).get(0);
		List<Rol> roles = servicio.traerRolesDeUsuario(bean.getId());
		List<Opcion> lista=servicio.traerEnlacesDeUsuario(bean.getId());
		session.setAttribute("datosUsuario",bean.getPaterno()+", "+bean.getNombres());
		session.setAttribute("Opciones",lista);
		session.setAttribute("Sede", bean.getSede().getNombre());
		session.setAttribute("CODIGO_USUARIO",bean.getId());
		return "index";

	}
}