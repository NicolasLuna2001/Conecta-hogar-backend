package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.EspecialidadRequestDTO;
import com.example.conecta_hogar.dto.EspecialidadResponseDTO;

import java.util.List;

public interface EspecialidadService {

    /* Crear */
    EspecialidadResponseDTO crearEspecialidad(EspecialidadRequestDTO request);

    /* Obtener todas */
    List<EspecialidadResponseDTO> obtenerEspecialidades();

    /* Obtener por ID */
    EspecialidadResponseDTO obtenerEspecialidadPorId(Long id);

    /* Actualizar */
    EspecialidadResponseDTO actualizarEspecialidad(Long id, EspecialidadRequestDTO request);

}