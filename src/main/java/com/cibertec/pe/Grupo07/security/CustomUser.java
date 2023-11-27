package com.cibertec.pe.Grupo07.security;

import com.cibertec.pe.Grupo07.model.Rol;
import com.cibertec.pe.Grupo07.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUser implements UserDetails {


    private Usuario usuario;

    public CustomUser(Usuario usuario) {
        super();
        this.usuario = usuario;
    }

/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRoles().toString());

        return Arrays.asList(authority);
    }*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Aquí, obtén los roles del usuario y agrégalos como SimpleGrantedAuthority
        // Supongo que los roles están almacenados en la entidad Usuario como una colección de objetos Rol
        // Debes adaptar esto según cómo estén modelados tus roles en tu base de datos
        
        for (Rol rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(rol.getNombre().toString()));
        }

        return authorities;*/
    	Set<GrantedAuthority> authorities = new HashSet<>();
        
        for (Rol rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
        }

        return authorities;
    }
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


		
	
}