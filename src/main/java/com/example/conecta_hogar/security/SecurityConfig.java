package com.example.conecta_hogar.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthFilter jwtAuthFilter; // <-- Se agrega la inyección del filtro JWT

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // API sin sesión en servidor, porque usarás JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Login público
                        .requestMatchers("/auth/**").permitAll()

                        // Registro público
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                        // Solo ADMIN puede listar todos los usuarios
                        .requestMatchers(HttpMethod.GET, "/usuarios")
                        .hasRole("ADMIN")

                        // Solo ADMIN puede filtrar por rol
                        .requestMatchers(HttpMethod.GET, "/usuarios/rol/**")
                        .hasRole("ADMIN")

                        // Solo ADMIN puede filtrar por estado
                        .requestMatchers(HttpMethod.GET, "/usuarios/estado/**")
                        .hasRole("ADMIN")

                        // Solo ADMIN puede cambiar el estado de una cuenta
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/*/estado")
                        .hasRole("ADMIN")

                        // Consultar un usuario por id:
                        // temporalmente, cualquier usuario autenticado
                        .requestMatchers(HttpMethod.GET, "/usuarios/*")
                        .authenticated()

                        // Actualizar usuario:
                        // temporalmente, cualquier usuario autenticado
                        .requestMatchers(HttpMethod.PUT, "/usuarios/*")
                        .authenticated()

                        // Cambiar contraseña:
                        // temporalmente, cualquier usuario autenticado
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/*/contrasena")
                        .authenticated()

                        // Cualquier otra ruta necesita token válido
                        .anyRequest().authenticated()
                )

                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                .authenticationProvider(authenticationProvider())

                // Valida el JWT antes del filtro estándar de Spring
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Se le pasa customUserDetailsService al constructor para evitar errores de Spring Security 3+
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }
}