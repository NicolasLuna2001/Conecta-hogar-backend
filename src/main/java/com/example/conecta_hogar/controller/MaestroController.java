package com.example.conecta_hogar.controller;

import com.example.conecta_hogar.dto.MaestroRequestDTO;
import com.example.conecta_hogar.dto.MaestroResponseDTO;
import com.example.conecta_hogar.service.MaestroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/maestros")
@AllArgsConstructor
public class MaestroController {

    private final MaestroService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaestroResponseDTO crearMaestro(@Valid @RequestBody MaestroRequestDTO request) {
        return service.crearMaestro(request);
    }

    @GetMapping
    public List<MaestroResponseDTO> obtenerMaestros() {
        return service.obtenerMaestros();
    }

    @GetMapping("/{id}")
    public MaestroResponseDTO maestroById(@PathVariable Long id) {
        return service.maestroById(id);
    }

    @PutMapping("/{id}")
    public MaestroResponseDTO actualizarMaestro(@PathVariable Long id, @Valid @RequestBody MaestroRequestDTO request) {
        return service.actualizarMaestro(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMaestro(@PathVariable Long id) {
        service.eliminarMaestro(id);
    }

    // --- ENDPOINTS ADICIONALES PARA EL FRONTEND ---

    @PutMapping("/{id}/me-gusta")
    public MaestroResponseDTO darMeGusta(@PathVariable Long id) {
        return service.darMeGusta(id);
    }

    @PutMapping("/{id}/no-me-gusta")
    public MaestroResponseDTO darNoMeGusta(@PathVariable Long id) {
        return service.darNoMeGusta(id);
    }

    @GetMapping("/top")
    public List<MaestroResponseDTO> obtenerTopMaestros() {
        return service.obtenerTopMaestros();
    }
}