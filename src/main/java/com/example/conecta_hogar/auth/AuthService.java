package com.example.conecta_hogar.auth;



import com.example.conecta_hogar.auth.LoginRequestDTO;
import com.example.conecta_hogar.auth.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);


}