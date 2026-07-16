package com.example.conecta_hogar.dto;

import com.example.conecta_hogar.model.Rol;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdminResponseDTO(


        String nombre,

        String apellido,

        String correo,

        String telefono,

        Rol rol,

        LocalDateTime fechaRegistro
) {
}
