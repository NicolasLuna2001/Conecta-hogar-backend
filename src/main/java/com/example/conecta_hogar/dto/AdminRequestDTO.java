package com.example.conecta_hogar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100)
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 100)
        String apellido,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Ingrese un correo válido")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String contrasena,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(
                regexp = "^(\\+56)?9\\d{8}$",
                message = "Ingrese un teléfono válido"
        )
        String telefono
) {
}
