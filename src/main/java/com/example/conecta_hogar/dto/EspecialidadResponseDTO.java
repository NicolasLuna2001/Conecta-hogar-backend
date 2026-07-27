package com.example.conecta_hogar.dto;

import lombok.Builder;

@Builder
public record EspecialidadResponseDTO(

        Long idEspecialidad,

        String nombre,

        String descripcion

) {
}