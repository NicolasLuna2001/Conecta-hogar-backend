package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.MaestroRequestDTO;
import com.example.conecta_hogar.dto.MaestroResponseDTO;
import com.example.conecta_hogar.mapper.MaestroMapper;
import com.example.conecta_hogar.model.MaestroModel;
import com.example.conecta_hogar.repository.MaestroRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class MaestroServiceImpl implements MaestroService {

    private final MaestroRepository repository;
    private final MaestroMapper mapper;
    private final PasswordEncoder passwordEncoder; // Inyección para encriptar contraseñas

    @Override
    public MaestroResponseDTO crearMaestro(MaestroRequestDTO request) {
        MaestroModel maestro = mapper.toModel(request);

        // ⬇️ ENCRIPTACIÓN DE CONTRASEÑA CON BCRYPT ⬇️
        if (request.contrasena() != null) {
            maestro.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

        MaestroModel guardado = repository.save(maestro);
        return mapper.toDTO(guardado);
    }

    @Override
    public List<MaestroResponseDTO> obtenerMaestros() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public MaestroResponseDTO maestroById(Long id) {
        MaestroModel maestro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado con ID: " + id));
        return mapper.toDTO(maestro);
    }

    @Override
    public MaestroResponseDTO actualizarMaestro(Long id, MaestroRequestDTO request) {
        MaestroModel maestro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado"));

        maestro.setNombre(request.nombre());
        maestro.setApellido(request.apellido());
        maestro.setCorreo(request.correo());
        maestro.setTelefono(request.telefono());
        maestro.setEspecialidad(request.especialidad());
        maestro.setDescripcion(request.descripcion());
        maestro.setFotoPerfil(request.fotoPerfil());

        // Si se envía una nueva contraseña al actualizar, también se encripta
        if (request.contrasena() != null && !request.contrasena().isBlank()) {
            maestro.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

        MaestroModel actualizado = repository.save(maestro);
        return mapper.toDTO(actualizado);
    }

    @Override
    public void eliminarMaestro(Long id) {
        MaestroModel maestro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado"));
        repository.delete(maestro);
    }

    @Override
    public MaestroResponseDTO darMeGusta(Long id) {
        MaestroModel maestro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado"));
        maestro.setMeGusta(maestro.getMeGusta() + 1);
        return mapper.toDTO(repository.save(maestro));
    }

    @Override
    public MaestroResponseDTO darNoMeGusta(Long id) {
        MaestroModel maestro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado"));
        maestro.setNoMeGusta(maestro.getNoMeGusta() + 1);
        return mapper.toDTO(repository.save(maestro));
    }

    @Override
    public List<MaestroResponseDTO> obtenerTopMaestros() {
        return repository.findTop5ByOrderByMeGustaDesc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}