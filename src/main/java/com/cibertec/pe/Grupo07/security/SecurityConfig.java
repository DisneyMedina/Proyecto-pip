package com.cibertec.pe.Grupo07.security;



import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
	

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService getDetailService() {
        return new CustomUserDetailsService();
    }


    @Bean
    public DaoAuthenticationProvider getdaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailService());
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/login", "/css/**", "/js/**").permitAll().permitAll()
                .requestMatchers("/user/**").authenticated().and()
                .formLogin().loginPage("/login").loginProcessingUrl("/userLogin")
                .defaultSuccessUrl("/usuarios").permitAll();


        return http.build();
    }
*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity.csrf().disable()
        .authorizeRequests(auth ->
        		{
        			auth.requestMatchers("/login","/","/index/**", "/css/**","/scss/**","/fonts/**","/vendor/**", "/js/**","/images/**").permitAll();
        			auth.requestMatchers("/index").hasAnyRole("Administrador", "JefePrestamista", "Prestamista");
        			auth.requestMatchers("/prestamistas/**","/prestamistas").hasAnyRole("Administrador", "JefePrestamista");
        			auth.requestMatchers("/usuarios/**","/usuarios").hasAnyRole("Administrador");
        			auth.anyRequest().authenticated();
        		})
        .formLogin(login ->
        login
        .loginPage("/login")
        .loginProcessingUrl("/userLogin")
        .defaultSuccessUrl("/indexAdmin")
        )
    	.exceptionHandling(exceptionHandling ->
        exceptionHandling
            .authenticationEntryPoint((request, response, authException) -> {
                response.sendRedirect("/"); // Redirige a la raíz si no está autenticado
            })
    );
    	return httpSecurity.build();
    }
    
}