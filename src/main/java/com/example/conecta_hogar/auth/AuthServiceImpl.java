package com.example.conecta_hogar.auth;

import com.example.conecta_hogar.security.CustomUserDetails;
import com.example.conecta_hogar.security.CustomUserDetailsService;
import com.example.conecta_hogar.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService; // Inyección de JwtService

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        // 1. Autenticamos el correo y contraseña con Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.correo(),
                        request.contrasena()
                )
        );

        // 2. Cargamos la información del usuario
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(request.correo());

        // 3. Generamos el token JWT real
        String jwtToken = jwtService.generateToken(userDetails);

        // 4. Retornamos la respuesta construida con el token generado
        return LoginResponseDTO.builder()
                .mensaje("Inicio de sesión exitoso")
                .token(jwtToken) // <-- Reemplazamos el null por el token generado
                .build();
    }
}