package com.example.conecta_hogar.mapper;

import com.example.conecta_hogar.dto.EspecialidadRequestDTO;
import com.example.conecta_hogar.dto.EspecialidadResponseDTO;
import com.example.conecta_hogar.model.EspecialidadModel;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadMapper {

    /* DTO -> Model */
    public EspecialidadModel toModel(EspecialidadRequestDTO request){

        EspecialidadModel especialidad = new EspecialidadModel();

        especialidad.setNombre(request.nombre());
        especialidad.setDescripcion(request.descripcion());

        return especialidad;
    }

    /* Model -> DTO */
    public EspecialidadResponseDTO toDTO(EspecialidadModel especialidad){

        return EspecialidadResponseDTO.builder()
                .idEspecialidad(especialidad.getIdEspecialidad())
                .nombre(especialidad.getNombre())
                .descripcion(especialidad.getDescripcion())
                .build();
    }

}