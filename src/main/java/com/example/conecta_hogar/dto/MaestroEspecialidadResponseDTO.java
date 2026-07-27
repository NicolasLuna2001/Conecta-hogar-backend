package com.example.conecta_hogar.dto;

import lombok.Builder;

@Builder
public record MaestroEspecialidadResponseDTO(

        Long id,

        Long idMaestro,

        String nombreMaestro,

        Long idEspecialidad,

        String nombreEspecialidad,

        Integer aniosExperiencia,

        Boolean esPrincipal

) {
}