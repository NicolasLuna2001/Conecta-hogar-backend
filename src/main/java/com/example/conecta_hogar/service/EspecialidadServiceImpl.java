package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.EspecialidadRequestDTO;
import com.example.conecta_hogar.dto.EspecialidadResponseDTO;
import com.example.conecta_hogar.mapper.EspecialidadMapper;
import com.example.conecta_hogar.model.EspecialidadModel;
import com.example.conecta_hogar.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService{

    private final EspecialidadRepository repository;
    private final EspecialidadMapper mapper;

    @Override
    public EspecialidadResponseDTO crearEspecialidad(EspecialidadRequestDTO request) {

        if(repository.existsByNombre(request.nombre())){
            throw new RuntimeException("La especialidad ya existe.");
        }

        EspecialidadModel especialidad = mapper.toModel(request);

        repository.save(especialidad);

        return mapper.toDTO(especialidad);
    }

    @Override
    public List<EspecialidadResponseDTO> obtenerEspecialidades() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public EspecialidadResponseDTO obtenerEspecialidadPorId(Long id) {

        EspecialidadModel especialidad = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Especialidad no encontrada."));

        return mapper.toDTO(especialidad);
    }

    @Override
    public EspecialidadResponseDTO actualizarEspecialidad(Long id,
                                                          EspecialidadRequestDTO request) {

        EspecialidadModel especialidad = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Especialidad no encontrada."));

        especialidad.setNombre(request.nombre());
        especialidad.setDescripcion(request.descripcion());

        repository.save(especialidad);

        return mapper.toDTO(especialidad);
    }

}