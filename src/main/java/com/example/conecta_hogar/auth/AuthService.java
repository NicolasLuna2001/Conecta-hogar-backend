package com.example.conecta_hogar.auth;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    LoginResponseDTO register(RegisterRequestDTO request);
}