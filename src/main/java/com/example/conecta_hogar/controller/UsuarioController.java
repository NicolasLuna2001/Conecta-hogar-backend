package com.example.conecta_hogar.controller;

import com.example.conecta_hogar.dto.UsuarioRequestDTO;
import com.example.conecta_hogar.dto.UsuarioResponseDTO;
import com.example.conecta_hogar.model.EstadoUsuario;
import com.example.conecta_hogar.model.Rol;
import com.example.conecta_hogar.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    /* Crear usuario */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO request) {


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearUsuario(request));
    }

    /* Obtener todos los usuarios */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {

        return ResponseEntity.ok(service.obtenerUsuarios());
    }

    /* Obtener usuario por ID */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.obtenerUsuarioPorId(id));
    }

    /* Actualizar usuario */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO request) {

        return ResponseEntity.ok(service.actualizarUsuario(id, request));
    }

    /* Cambiar estado */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoUsuario estado) {

        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    /* Cambiar contraseña */
    @PatchMapping("/{id}/contrasena")
    public ResponseEntity<String> cambiarContrasena(
            @PathVariable Long id,
            @RequestParam String contrasenaActual,
            @RequestParam String nuevaContrasena) {

        service.cambiarContrasena(id, contrasenaActual, nuevaContrasena);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

    /* Obtener usuarios por rol */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerPorRol(
            @PathVariable Rol rol) {

        return ResponseEntity.ok(service.obtenerUsuariosPorRol(rol));
    }

    /* Obtener usuarios por estado */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerPorEstado(
            @PathVariable EstadoUsuario estado) {

        return ResponseEntity.ok(service.obtenerUsuariosPorEstado(estado));
    }

}