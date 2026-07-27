package com.example.conecta_hogar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecialidadRequestDTO(

        @NotBlank(message = "El nombre de la especialidad es obligatorio")
        @Size(max = 100, message = "No puede superar los 100 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion

) {}