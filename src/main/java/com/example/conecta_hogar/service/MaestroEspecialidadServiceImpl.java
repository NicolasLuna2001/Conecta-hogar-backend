package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.MaestroEspecialidadRequestDTO;
import com.example.conecta_hogar.dto.MaestroEspecialidadResponseDTO;
import com.example.conecta_hogar.mapper.MaestroEspecialidadMapper;
import com.example.conecta_hogar.model.EspecialidadModel;
import com.example.conecta_hogar.model.MaestroEspecialidadModel;
import com.example.conecta_hogar.model.MaestroModel;
import com.example.conecta_hogar.repository.EspecialidadRepository;
import com.example.conecta_hogar.repository.MaestroEspecialidadRepository;
import com.example.conecta_hogar.repository.MaestroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaestroEspecialidadServiceImpl
        implements MaestroEspecialidadService {

    private final MaestroEspecialidadRepository repository;
    private final MaestroRepository maestroRepository;
    private final EspecialidadRepository especialidadRepository;
    private final MaestroEspecialidadMapper mapper;

    @Override
    public MaestroEspecialidadResponseDTO asignarEspecialidad(
            MaestroEspecialidadRequestDTO request
    ) {

        if (repository
                .existsByMaestroIdUsuarioAndEspecialidadIdEspecialidad(
                        request.idMaestro(),
                        request.idEspecialidad()
                )) {

            throw new RuntimeException(
                    "El maestro ya tiene asignada esta especialidad."
            );
        }

        MaestroModel maestro = maestroRepository
                .findById(request.idMaestro())
                .orElseThrow(() ->
                        new RuntimeException("Maestro no encontrado.")
                );

        EspecialidadModel especialidad = especialidadRepository
                .findById(request.idEspecialidad())
                .orElseThrow(() ->
                        new RuntimeException("Especialidad no encontrada.")
                );

        MaestroEspecialidadModel relacion =
                new MaestroEspecialidadModel();

        relacion.setMaestro(maestro);
        relacion.setEspecialidad(especialidad);
        relacion.setAniosExperiencia(request.aniosExperiencia());

        relacion.setEsPrincipal(
                Boolean.TRUE.equals(request.esPrincipal())
        );

        MaestroEspecialidadModel guardada =
                repository.save(relacion);

        return mapper.toDTO(guardada);
    }

    @Override
    public List<MaestroEspecialidadResponseDTO>
    obtenerEspecialidadesPorMaestro(Long idMaestro) {

        if (!maestroRepository.existsById(idMaestro)) {
            throw new RuntimeException("Maestro no encontrado.");
        }

        return repository.findByMaestroIdUsuario(idMaestro)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<MaestroEspecialidadResponseDTO>
    obtenerMaestrosPorEspecialidad(Long idEspecialidad) {

        if (!especialidadRepository.existsById(idEspecialidad)) {
            throw new RuntimeException(
                    "Especialidad no encontrada."
            );
        }

        return repository
                .findByEspecialidadIdEspecialidad(idEspecialidad)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public MaestroEspecialidadResponseDTO actualizarRelacion(
            Long id,
            MaestroEspecialidadRequestDTO request
    ) {

        MaestroEspecialidadModel relacion = repository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Relación maestro-especialidad no encontrada."
                        )
                );

        relacion.setAniosExperiencia(request.aniosExperiencia());

        relacion.setEsPrincipal(
                Boolean.TRUE.equals(request.esPrincipal())
        );

        MaestroEspecialidadModel actualizada =
                repository.save(relacion);

        return mapper.toDTO(actualizada);
    }

    @Transactional
    @Override
    public void eliminarEspecialidadDeMaestro(
            Long idMaestro,
            Long idEspecialidad
    ) {

        boolean existe = repository
                .existsByMaestroIdUsuarioAndEspecialidadIdEspecialidad(
                        idMaestro,
                        idEspecialidad
                );

        if (!existe) {
            throw new RuntimeException(
                    "El maestro no tiene asignada esta especialidad."
            );
        }

        repository
                .deleteByMaestroIdUsuarioAndEspecialidadIdEspecialidad(
                        idMaestro,
                        idEspecialidad
                );
    }
}