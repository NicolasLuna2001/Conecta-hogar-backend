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
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // ⬇️ RECURSOS ESTÁTICOS / FOTOS SUBIDAS PÚBLICAS ⬇️
                        .requestMatchers("/uploads/**").permitAll()

                        // Login público
                        .requestMatchers("/auth/**").permitAll()

                        // Registro público de usuarios
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                        // Solo ADMIN puede listar todos los usuarios
                        .requestMatchers(HttpMethod.GET, "/usuarios")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Solo ADMIN puede filtrar por rol
                        .requestMatchers(HttpMethod.GET, "/usuarios/rol/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Solo ADMIN puede filtrar por estado
                        .requestMatchers(HttpMethod.GET, "/usuarios/estado/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Solo ADMIN puede cambiar el estado de una cuenta
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/*/estado")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Consultar un usuario por id: cualquier autenticado
                        .requestMatchers(HttpMethod.GET, "/usuarios/*")
                        .authenticated()

                        // Actualizar usuario: cualquier autenticado
                        .requestMatchers(HttpMethod.PUT, "/usuarios/*")
                        .authenticated()

                        // Cambiar contraseña: cualquier autenticado
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/*/contrasena")
                        .authenticated()

                        /* ESPECIALIDADES */

                        .requestMatchers(HttpMethod.GET, "/especialidades", "/especialidades/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/especialidades")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/especialidades/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/especialidades/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        /* MAESTRO - ESPECIALIDAD */

                        .requestMatchers(HttpMethod.GET, "/maestro-especialidades/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/maestro-especialidades")
                        .hasAnyAuthority("MAESTRO", "ROLE_MAESTRO", "ADMIN", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/maestro-especialidades/**")
                        .hasAnyAuthority("MAESTRO", "ROLE_MAESTRO", "ADMIN", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/maestro-especialidades/**")
                        .hasAnyAuthority("MAESTRO", "ROLE_MAESTRO", "ADMIN", "ROLE_ADMIN")

                        /* MAESTROS */

                        // Crear maestro normal o con foto: permite MAESTRO o ADMIN (con o sin prefijo ROLE_)
                        // ⬇️ SE AGREGÓ "/maestros/con-foto" AQUÍ ⬇️
                        .requestMatchers(HttpMethod.POST, "/maestros", "/maestros/con-foto")
                        .hasAnyAuthority("MAESTRO", "ROLE_MAESTRO", "ADMIN", "ROLE_ADMIN")

                        // Consultar maestros sigue siendo público
                        .requestMatchers(
                                HttpMethod.GET,
                                "/maestros",
                                "/maestros/top",
                                "/maestros/*"
                        ).permitAll()

                        // Actualizar perfil
                        .requestMatchers(HttpMethod.PUT, "/maestros/*")
                        .hasAnyAuthority("MAESTRO", "ROLE_MAESTRO", "ADMIN", "ROLE_ADMIN")

                        // Eliminar
                        .requestMatchers(HttpMethod.DELETE, "/maestros/*")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Valoraciones (Me gusta / No me gusta)
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/maestros/*/me-gusta",
                                "/maestros/*/no-me-gusta"
                        ).hasAnyAuthority("CLIENTE", "ROLE_CLIENTE")

                        .anyRequest().authenticated()
                )

                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                .authenticationProvider(authenticationProvider())

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