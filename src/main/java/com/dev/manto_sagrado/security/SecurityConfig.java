package com.dev.manto_sagrado.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // TIRA A AUTENTICAÇÃO NO POSTMAN
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permite todas as requisições
                .csrf(csrf -> csrf.disable()); // Desativa CSRF (opcional para APIs)

        return http.build();
    }
}
