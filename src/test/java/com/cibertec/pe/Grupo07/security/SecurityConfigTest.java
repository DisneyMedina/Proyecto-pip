package com.cibertec.pe.Grupo07.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class SecurityConfigTest {
    @Test
    public void EncriptarPass() {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Generar una contraseña codificada
        String rawPassword = "12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Imprimir la contraseña codificada
        System.out.println("Contraseña en texto claro: " + rawPassword);
        System.out.println("Contraseña codificada: " + encodedPassword);

        // Verificar si una contraseña coincide con la versión codificada
        boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("¿La contraseña coincide? " + isMatch);
    }
}