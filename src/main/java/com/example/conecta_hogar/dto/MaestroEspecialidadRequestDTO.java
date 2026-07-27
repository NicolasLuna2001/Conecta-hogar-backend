package com.example.conecta_hogar.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MaestroEspecialidadRequestDTO(

        @NotNull(message = "El id del maestro es obligatorio")
        Long idMaestro,

        @NotNull(message = "El id de la especialidad es obligatorio")
        Long idEspecialidad,

        @Min(
                value = 0,
                message = "Los años de experiencia no pueden ser negativos"
        )
        Integer aniosExperiencia,

        Boolean esPrincipal

) {
}