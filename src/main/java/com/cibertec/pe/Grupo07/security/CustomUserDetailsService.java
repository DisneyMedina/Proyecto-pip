package com.cibertec.pe.Grupo07.security;

import com.cibertec.pe.Grupo07.model.Usuario;
import com.cibertec.pe.Grupo07.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsuarioRepository userRepository;

    public CustomUserDetailsService() {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findUserByEmail(username);
        if (usuario == null || usuario.getEstado()==(short)0) {
            throw new UsernameNotFoundException("usuario no encontrado");
        } else {
            return new CustomUser(usuario);
        }

    }
 /*   @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("No se encontro ningun usuario");
        }
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                  //      .password(passwordEncoder().encode(user.getPassword()))
                        .password(user.getPassword())
                        .roles("USUARIO")
                        .build();

        return userDetails;
    }*/


}