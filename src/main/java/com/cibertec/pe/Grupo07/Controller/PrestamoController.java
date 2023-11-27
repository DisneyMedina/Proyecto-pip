package com.cibertec.pe.Grupo07.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.pe.Grupo07.model.Prestamo;
import com.cibertec.pe.Grupo07.repository.TipoPrestamoRepository;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import com.cibertec.pe.Grupo07.service.PrestamoService;
import com.cibertec.pe.Grupo07.service.RolService;

@Controller
@RequestMapping
public class PrestamoController {

	@Autowired
	private PrestamoService prestamoService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolService rolService;

	@Autowired
	private TipoPrestamoRepository tipoPrestamoRepository;

	@GetMapping("/prestamos")
	
	public String listarPrestamos(Model model) {
		List<Prestamo> prestamos = prestamoService.listarPrestamos();
		model.addAttribute("prestamos", prestamos);
		return "prestamos";
	}
/*
	@GetMapping("/prestamos/nuevo")
	public String mostrarFormularioNuevoPrestamo(Model model) {
		Prestamo prestamo = new Prestamo();
		List<TipoPrestamo> listaTipoPrestamo = tipoPrestamoRepository.findByOrderByDescripcionAsc();
		model.addAttribute("listaTipoPrestamo", listaTipoPrestamo);
		model.addAttribute("prestamo", prestamo);

		return "crear_prestamo";
	}

	@PostMapping("/prestamos")
	@ResponseBody
	public Map<?, ?> registrar(Usuario usuario, Prestamo prestamo, HttpSession httpSession) {

		HashMap<String, String> map = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername();

			TipoPrestamo tipoPrestamo = new TipoPrestamo();
			tipoPrestamo.setId(1L);
			// tipoPrestamo.setDescripcion("efectivo");

			Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(username);

			prestamo.setMonto(prestamo.getMonto());
			prestamo.setMonto(prestamo.getMonto());
			Rol rol = rolService.obtenerRolId(3L);
			usuario.setFlag(0);
			usuario.setEstado((short) 1);
			usuario.setFechaRegistro(new Date());
			usuario.addRol(rol);

			usuario.setUsuarioRegistro(objUsuario);
			usuario.setUsuarioModificacion(objUsuario);

			map = new HashMap<String, String>();

			Prestamo prestamo1 = prestamoService.registrarPrestamo(prestamo);
		} catch (Exception e) {
			map.put("MENSAJE", "Error en el registro");
		}
		map.put("MENSAJE", "Registro exitoso");

		return map;

	}
*/
	@GetMapping("/prestamo/editar/{id}")
	public String editPrestamoForm(@PathVariable Long id, Model model) {
		Prestamo prestamo = prestamoService.obtenerIdPrestamo(id);
		model.addAttribute("prestamo", prestamo);
		// Puedes cargar las opciones para las relaciones aquí si es necesario.
		return "editar_prestamo";
	}

	@PostMapping("/prestamo/editar/{id}")
	public String editPrestamo(@PathVariable Long id, @ModelAttribute Prestamo prestamo) {
		prestamoService.actualizaPrestamo(prestamo);
		return "redirect:/prestamos";
	}

	@GetMapping("/prestamo/delete/{id}")
	public String deletePrestamo(@PathVariable Long id) {
		prestamoService.eliminarPrestamo(id);
		return "redirect:/prestamos";
	}

}
