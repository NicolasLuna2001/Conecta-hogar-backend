package com.example.conecta_hogar.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // Maneja error de contraseña/correo incorrectos devolviendo un 401 claro
            System.err.println("❌ Credenciales incorrectas para el intento de login: " + request);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Correo o contraseña incorrectos."));
        } catch (Exception e) {
            // Maneja otros errores (ej. usuario no encontrado, cuenta inactiva)
            System.err.println("❌ Error en el proceso de login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage() != null ? e.getMessage() : "Error al iniciar sesión."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            LoginResponseDTO response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error en el proceso de registro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage() != null ? e.getMessage() : "No se pudo completar el registro."));
        }
    }
}