package com.example.conecta_hogar.dto;

import com.example.conecta_hogar.model.Rol;

public record AdminResponseDTO(


        String nombre,

        String apellido,

        String correo,

        String telefono,

        Rol rol
) {
}
