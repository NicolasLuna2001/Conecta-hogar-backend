package com.example.conecta_hogar.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        /*se reciben las credenciales ingresadas*/

        @NotBlank(message = "Debe ingresar un correo")
        @Email(message = "Correo inválido")
        String correo,

        @NotBlank(message = "Debe ingresar una contraseña")
        String contrasena

) {
}