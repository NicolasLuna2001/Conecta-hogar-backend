package com.example.conecta_hogar.dto;

import com.example.conecta_hogar.model.EstadoUsuario;
import com.example.conecta_hogar.model.Rol;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioResponseDTO(

        /*AQUÍ SE MANEJAN LOS DATOS QUE LE DEVOLVEMOS AL USUARIO*/

        String nombre,

        String apellido,

        String correo,

        String telefono,

        Rol rol,

        EstadoUsuario estado,

        LocalDateTime fechaRegistro

) {
}
