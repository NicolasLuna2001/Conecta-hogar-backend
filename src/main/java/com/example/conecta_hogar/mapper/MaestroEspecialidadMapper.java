package com.example.conecta_hogar.mapper;

import com.example.conecta_hogar.dto.MaestroEspecialidadResponseDTO;
import com.example.conecta_hogar.model.MaestroEspecialidadModel;
import org.springframework.stereotype.Component;

@Component
public class MaestroEspecialidadMapper {

    public MaestroEspecialidadResponseDTO toDTO(
            MaestroEspecialidadModel relacion
    ) {

        return MaestroEspecialidadResponseDTO.builder()
                .id(relacion.getId())
                .idMaestro(relacion.getMaestro().getIdUsuario())
                .nombreMaestro(
                        relacion.getMaestro().getNombre()
                                + " "
                                + relacion.getMaestro().getApellido()
                )
                .idEspecialidad(
                        relacion.getEspecialidad().getIdEspecialidad()
                )
                .nombreEspecialidad(
                        relacion.getEspecialidad().getNombre()
                )
                .aniosExperiencia(relacion.getAniosExperiencia())
                .esPrincipal(relacion.getEsPrincipal())
                .build();
    }
}