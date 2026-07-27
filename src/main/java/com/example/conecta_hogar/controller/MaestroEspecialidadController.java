package com.example.conecta_hogar.controller;

import com.example.conecta_hogar.dto.MaestroEspecialidadRequestDTO;
import com.example.conecta_hogar.dto.MaestroEspecialidadResponseDTO;
import com.example.conecta_hogar.service.MaestroEspecialidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maestro-especialidades")
@RequiredArgsConstructor
public class MaestroEspecialidadController {

    private final MaestroEspecialidadService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('MAESTRO', 'ADMIN')")
    public ResponseEntity<MaestroEspecialidadResponseDTO> asignar(
            @Valid
            @RequestBody MaestroEspecialidadRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.asignarEspecialidad(request));
    }

    @GetMapping("/maestro/{idMaestro}")
    public ResponseEntity<List<MaestroEspecialidadResponseDTO>>
    obtenerPorMaestro(
            @PathVariable Long idMaestro
    ) {

        return ResponseEntity.ok(
                service.obtenerEspecialidadesPorMaestro(idMaestro)
        );
    }

    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<MaestroEspecialidadResponseDTO>>
    obtenerPorEspecialidad(
            @PathVariable Long idEspecialidad
    ) {

        return ResponseEntity.ok(
                service.obtenerMaestrosPorEspecialidad(idEspecialidad)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MAESTRO', 'ADMIN')")
    public ResponseEntity<MaestroEspecialidadResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody MaestroEspecialidadRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.actualizarRelacion(id, request)
        );
    }

    @DeleteMapping(
            "/maestro/{idMaestro}/especialidad/{idEspecialidad}"
    )
    @PreAuthorize("hasAnyRole('MAESTRO', 'ADMIN')")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long idMaestro,
            @PathVariable Long idEspecialidad
    ) {

        service.eliminarEspecialidadDeMaestro(
                idMaestro,
                idEspecialidad
        );

        return ResponseEntity.noContent().build();
    }
}