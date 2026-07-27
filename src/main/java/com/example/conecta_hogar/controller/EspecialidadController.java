package com.example.conecta_hogar.controller;

import com.example.conecta_hogar.dto.EspecialidadRequestDTO;
import com.example.conecta_hogar.dto.EspecialidadResponseDTO;
import com.example.conecta_hogar.service.EspecialidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecialidadResponseDTO> crearEspecialidad(
            @Valid @RequestBody EspecialidadRequestDTO request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearEspecialidad(request));
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDTO>> listar(){

        return ResponseEntity.ok(service.obtenerEspecialidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> obtenerPorId(
            @PathVariable Long id){

        return ResponseEntity.ok(service.obtenerEspecialidadPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecialidadResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EspecialidadRequestDTO request){

        return ResponseEntity.ok(
                service.actualizarEspecialidad(id, request)
        );
    }

}
