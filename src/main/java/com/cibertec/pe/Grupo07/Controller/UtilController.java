package com.cibertec.pe.Grupo07.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.pe.Grupo07.model.Banco;
import com.cibertec.pe.Grupo07.model.BancoHasUsuario;
import com.cibertec.pe.Grupo07.model.Sede;
import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.BancoHasUsuarioRepository;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import com.cibertec.pe.Grupo07.service.BancoHasUsuarioService;
import com.cibertec.pe.Grupo07.service.BancoService;
import com.cibertec.pe.Grupo07.service.SedeService;
import com.cibertec.pe.Grupo07.util.FunctionUtil;

@Controller
@RequestMapping()
public class UtilController {

    @Autowired
    private SedeService sedeService;

    @Autowired
    private BancoService bancoService;


    @Autowired
    private BancoHasUsuarioService bancoHasUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BancoHasUsuarioRepository bancoHasUsuarioRepository;

    /*

        @GetMapping("/listaSede")
        @ResponseBody
        public List<Sede> listaPais() {
            return sedeService.listaSedes();
        }*/
    @GetMapping("/listaSede")
    @ResponseBody
    public List<Sede> listaPais2() {
        return sedeService.listaSedes();
    }

    @GetMapping("/listaBanco")
    @ResponseBody
    public List<Banco> listaBanco() {
        return bancoService.listarBanco();
    }
    @GetMapping("/buscaNumeroCuenta")
	@ResponseBody
	public String validaEmpleadoRegistra(String cuenta) {
		List<BancoHasUsuario> lstSalida = bancoHasUsuarioService.listarcuentaBanco(
													cuenta);
		if(lstSalida.isEmpty()) {
			return "{\"valid\":true}";
		}else {
			return "{\"valid\":false}";
		}
	}
    



@GetMapping("/listaDetalleBancoUsuario")
    @ResponseBody
    public List<BancoHasUsuario> listarcuentaBanco() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	Usuario objUsuario = (Usuario) usuarioRepository.findUserByEmail(userDetails.getUsername());
      

        return bancoHasUsuarioRepository.listarcuentaBanco(objUsuario.getId());
    }

@GetMapping("/validarFormatoMonto")
@ResponseBody
public String validaMonto(@RequestParam("monto") String monto) {
    try {
        double montoValor = Double.parseDouble(monto);
        if (montoValor >= 10 && montoValor <= 2500) {
            return "{\"valid\":true}";
        } else {
            return "{\"valid\":false}";
        }
    } catch (NumberFormatException e) {
        return "{\"valid\":false}";
    }
}
@GetMapping("/validarFechaInicio")
@ResponseBody
public String validaFecha(String fechaInicioPrestamo) {
	if(FunctionUtil.isMayorDiaActual(fechaInicioPrestamo)) {
		return "{\"valid\":true}";
	}else {
		return "{\"valid\":false}";
	}
}


@GetMapping("/validarFormatoDias")
@ResponseBody
public String validaDias(@RequestParam("dias") String dias) {
	try {
        int numeroDias = Integer.parseInt(dias);
        if (numeroDias >= 1 && numeroDias <= 90) {
            return "{\"valid\":true}";
        } else {
            return "{\"valid\":false}";
        }
    } catch (NumberFormatException e) {
        return "{\"valid\":false}";
    }
}
}
