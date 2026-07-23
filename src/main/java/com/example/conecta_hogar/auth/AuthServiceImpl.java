package com.example.conecta_hogar.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(//TOQUEN QUE CONTIENE LAS CREDENCIALES
                        request.correo(),
                        request.contrasena()
                )
        );

        return LoginResponseDTO.builder()
                .mensaje("Inicio de sesión exitoso")
                .token(null) // luego aquí irá el JWT
                .build();
    }
}