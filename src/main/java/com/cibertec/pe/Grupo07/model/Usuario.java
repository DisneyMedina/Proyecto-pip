package com.cibertec.pe.Grupo07.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;

    private String nombres;
    private String password;   
    private Short estado;
    private String email;
    private Integer flag;
    private String materno;
    private String paterno;
    private String numeroDocumento;
    private String telefono;
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSede")
    private Sede sede;
    
    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro")
    private Usuario usuarioRegistro; // Representa al usuario que lo registró
    @ManyToOne
    @JoinColumn(name = "idUsuarioModificacion")
    private Usuario usuarioModificacion; // Representa al usuario que lo registró
    
    
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaRegistro;
    @Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaModificacion;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private Date fechaNacimiento;
    
    @OneToMany(mappedBy = "usuario")
    private List<BancoHasUsuario> bancoHasUsuarios;

    @OneToMany(mappedBy = "usuarioRegistro")
    private List<Cuota> cuotas;

    @OneToMany(mappedBy = "usuarioPrestamista")
    private List<Prestamo> prestamosPrestamista;

    @OneToMany(mappedBy = "usuarioPrestatario")
    private List<Prestamo> prestamosPrestatario;

    @OneToMany(mappedBy = "usuarioRegistro")
    private List<Pago> pagos;

    @OneToMany(mappedBy = "usuarioLider")
    private List<Grupo> gruposLiderados;

    @OneToMany(mappedBy = "usuarioRegistro")
    private List<Grupo> gruposRegistrados;

    @OneToMany(mappedBy = "usuarioRegistro")
    private List<Solicitud> solicitudesRegistradas;
    
    
    
    public String getNombreCompleto() {
		return nombres.concat(" ").concat(paterno).concat(" ").concat(materno);
	}
    
    public void addRol(Rol rol) {
    	this.roles.add(rol);
    }
    public void eliminarRol(Rol rol) {
    	this.roles.remove(rol);
    }
    
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_has_rol", joinColumns = @JoinColumn(name ="idUsuario"), inverseJoinColumns = @JoinColumn(name ="idRol"))
    private Set<Rol> roles = new HashSet<>();


	public Usuario(String nombres, String password, Short estado, String email, Integer flag, String materno,
			String paterno, String numeroDocumento, String telefono, Date fechaNacimiento) {
		super();
		this.nombres = nombres;
		this.password = password;
		this.estado = estado;
		this.email = email;
		this.flag = flag;
		this.materno = materno;
		this.paterno = paterno;
		this.numeroDocumento = numeroDocumento;
		this.telefono = telefono;
		this.fechaRegistro = new Date();
		this.fechaNacimiento = fechaNacimiento;
	}

	
/*
    private Collection<Rol> roles;
    public Collection<Rol> getRoles() {
        return roles;
    }*/
}
