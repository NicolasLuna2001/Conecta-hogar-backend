package com.example.conecta_hogar.mapper;

import com.example.conecta_hogar.dto.MaestroRequestDTO;
import com.example.conecta_hogar.dto.MaestroResponseDTO;
import com.example.conecta_hogar.model.MaestroModel;
import com.example.conecta_hogar.model.Rol;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MaestroMapper {

    public MaestroModel toModel(MaestroRequestDTO request) {
        MaestroModel maestro = new MaestroModel();

        // Campos heredados de UsuarioModel
        maestro.setNombre(request.nombre());
        maestro.setApellido(request.apellido());
        maestro.setCorreo(request.correo());
        maestro.setContrasena(request.contrasena());
        maestro.setTelefono(request.telefono());
        maestro.setRol(Rol.MAESTRO);
        maestro.setFechaRegistro(LocalDateTime.now());

        // Campos propios de MaestroModel
        maestro.setEspecialidad(request.especialidad());
        maestro.setDescripcion(request.descripcion());
        maestro.setFotoPerfil(request.fotoPerfil());
        maestro.setActivo(true);
        maestro.setMeGusta(0);  // Inicialización segura
        maestro.setNoMeGusta(0); // Inicialización segura

        return maestro;
    }

    public MaestroResponseDTO toDTO(MaestroModel model) {
        return MaestroResponseDTO.builder()
                .idUsuario(model.getIdUsuario()) // Heredado
                .nombre(model.getNombre())
                .apellido(model.getApellido())
                .correo(model.getCorreo())
                .telefono(model.getTelefono())
                .especialidad(model.getEspecialidad())
                .descripcion(model.getDescripcion())
                .fotoPerfil(model.getFotoPerfil())
                .activo(model.getActivo())
                .meGusta(model.getMeGusta())
                .noMeGusta(model.getNoMeGusta())
                .build();
    }
}