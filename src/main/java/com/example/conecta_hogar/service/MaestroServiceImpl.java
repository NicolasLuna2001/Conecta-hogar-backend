package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.MaestroRequestDTO;
import com.example.conecta_hogar.dto.MaestroResponseDTO;
import com.example.conecta_hogar.mapper.MaestroMapper;
import com.example.conecta_hogar.model.MaestroModel;
import com.example.conecta_hogar.repository.MaestroRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MaestroServiceImpl implements MaestroService {

    private final MaestroRepository repository;
    private final MaestroMapper mapper;
    private final PasswordEncoder passwordEncoder;

    // Directorio donde se guardarán las fotos localmente
    private final String UPLOAD_DIR = "uploads/";

    @Override
    public MaestroResponseDTO crearMaestro(MaestroRequestDTO request) {
        MaestroModel maestro = mapper.toModel(request);

        if (request.contrasena() != null) {
            maestro.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

        MaestroModel guardado = repository.save(maestro);
        return mapper.toDTO(guardado);
    }

    // ⬇️ NUEVO MÉTODO: Para guardar el maestro junto con su foto local ⬇️
    @Override
    public MaestroResponseDTO crearMaestroConFoto(MaestroRequestDTO request, MultipartFile foto) {
        MaestroModel maestro = mapper.toModel(request);

        if (request.contrasena() != null) {
            maestro.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

        // Procesar y guardar la imagen localmente
        if (foto != null && !foto.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Crea la carpeta 'uploads' si no existe
                }

                // Generar un nombre único para evitar duplicados
                String nombreArchivo = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path filePath = uploadPath.resolve(nombreArchivo);

                // Copiar el archivo al directorio local
                Files.copy(foto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Guardar la ruta/nombre del archivo en el modelo
                maestro.setFotoPerfil("/uploads/" + nombreArchivo);

            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen localmente", e);
            }
        }

        MaestroModel guardado = repository.save(maestro);
        return mapper.toDTO(guardado);
    }

    // ⬇️ NUEVO MÉTODO: Para actualizar ÚNICAMENTE la foto de un maestro existente ⬇️
    @Override
    public MaestroResponseDTO actualizarFotoMaestro(Long id, MultipartFile foto) {
        MaestroModel maestro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maestro no encontrado con ID: " + id));

        if (foto != null && !foto.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String nombreArchivo = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path filePath = uploadPath.resolve(nombreArchivo);

                Files.copy(foto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Se asigna la nueva foto al maestro encontrado
                maestro.setFotoPerfil("/uploads/" + nombreArchivo);

            } catch (IOException e) {
                throw new RuntimeException("Error al actualizar la imagen localmente", e);
            }
        }

        MaestroModel actualizado = repository.save(maestro);
        return mapper.toDTO(actualizado);
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