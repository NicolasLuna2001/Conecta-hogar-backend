package com.example.conecta_hogar.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record RegisterRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "El RUT es obligatorio")
        String rut,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Debe ser un correo válido")
        String correo,

        @NotBlank(message = "El teléfono es obligatorio")
        String telefono,

        String direccion,

        @JsonProperty("password") // Acepta "password" en el JSON y lo guarda en 'password'
        String password,

        String tipoUsuario,

        List<String> especialidades
) {}