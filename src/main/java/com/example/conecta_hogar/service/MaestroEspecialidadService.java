package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.MaestroEspecialidadRequestDTO;
import com.example.conecta_hogar.dto.MaestroEspecialidadResponseDTO;

import java.util.List;

public interface MaestroEspecialidadService {

    MaestroEspecialidadResponseDTO asignarEspecialidad(
            MaestroEspecialidadRequestDTO request
    );

    List<MaestroEspecialidadResponseDTO>
    obtenerEspecialidadesPorMaestro(Long idMaestro);

    List<MaestroEspecialidadResponseDTO>
    obtenerMaestrosPorEspecialidad(Long idEspecialidad);

    MaestroEspecialidadResponseDTO actualizarRelacion(
            Long id,
            MaestroEspecialidadRequestDTO request
    );

    void eliminarEspecialidadDeMaestro(
            Long idMaestro,
            Long idEspecialidad
    );
}