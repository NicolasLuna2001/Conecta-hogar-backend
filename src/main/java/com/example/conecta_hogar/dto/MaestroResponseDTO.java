package com.example.conecta_hogar.dto;

import lombok.Builder;

@Builder
public record MaestroResponseDTO(
        Long idUsuario,
        String nombre,
        String apellido,
        String correo,
        String telefono,
        String especialidad,
        String descripcion,
        String fotoPerfil,
        Boolean activo,
        Integer meGusta,
        Integer noMeGusta,
        String estado
) {}