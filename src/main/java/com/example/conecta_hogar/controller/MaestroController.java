package com.example.conecta_hogar.controller;

import com.example.conecta_hogar.dto.MaestroRequestDTO;
import com.example.conecta_hogar.dto.MaestroResponseDTO;
import com.example.conecta_hogar.service.MaestroService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // ⬇️ ENDPOINT PARA CREAR MAESTRO SUBIENDO FOTO LOCAL ⬇️
    @PostMapping(value = "/con-foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MaestroResponseDTO crearMaestroConFoto(
            @Valid @RequestPart("maestro") MaestroRequestDTO request,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        return service.crearMaestroConFoto(request, foto);
    }

    //  NUEVO ENDPOINT PARA ACTUALIZAR LA FOTO DE UN MAESTRO EXISTENTE 
    @PutMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MaestroResponseDTO actualizarFotoMaestro(
            @PathVariable Long id,
            @RequestPart("foto") MultipartFile foto) {
        return service.actualizarFotoMaestro(id, foto);
    }

    @GetMapping
    public List<MaestroResponseDTO> obtenerMaestros() {
        return service.obtenerMaestros();
    }

    @GetMapping("/top")
    public List<MaestroResponseDTO> obtenerTopMaestros() {
        return service.obtenerTopMaestros();
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

    // --- ENDPOINTS DE VALORACIONES (CORREGIDOS A @PatchMapping SEGÚN SECURITYCONFIG) ---

    @PatchMapping("/{id}/me-gusta")
    public MaestroResponseDTO darMeGusta(@PathVariable Long id) {
        return service.darMeGusta(id);
    }

    @PatchMapping("/{id}/no-me-gusta")
    public MaestroResponseDTO darNoMeGusta(@PathVariable Long id) {
        return service.darNoMeGusta(id);
    }
}
