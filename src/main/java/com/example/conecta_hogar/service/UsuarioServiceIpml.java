package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.UsuarioRequestDTO;
import com.example.conecta_hogar.dto.UsuarioResponseDTO;
import com.example.conecta_hogar.mapper.UsuarioMapper;
import com.example.conecta_hogar.model.EstadoUsuario;
import com.example.conecta_hogar.model.Rol;
import com.example.conecta_hogar.model.UsuarioModel;
import com.example.conecta_hogar.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
/*crea el constructor*/
@AllArgsConstructor
public class UsuarioServiceIpml implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    /* Crear usuario */
    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {

        // Validar correo duplicado
        if (usuarioRepository.existsByCorreo(request.correo())) {
            throw new RuntimeException("El correo ya se encuentra registrado.");
        }

        // Convertir DTO a Modelo
        UsuarioModel usuario = usuarioMapper.toModel(request);

        // Encriptar contraseña
        usuario.setContrasena(
                passwordEncoder.encode(request.contrasena())
        );

        // --- ASIGNACIÓN DE ROL DINÁMICO ---
        // Si la petición trae un rol, lo asignamos; si viene nulo, asignamos CLIENTE por defecto.
        if (request.rol() != null) {
            usuario.setRol(request.rol());
        } else {
            usuario.setRol(Rol.CLIENTE);
        }

        // Estado y Fecha de Registro por defecto
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setFechaRegistro(LocalDateTime.now());

        // Guardar
        UsuarioModel usuarioGuardado = usuarioRepository.save(usuario);

        // Retornar ResponseDTO
        return usuarioMapper.toDTO(usuarioGuardado);
    }

    /* Obtener todos los usuarios */
    @Override
    public List<UsuarioResponseDTO> obtenerUsuarios() {

        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    /* Obtener usuario por ID */
    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        return usuarioMapper.toDTO(usuario);
    }

    /* Actualizar usuario */
    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO request) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setCorreo(request.correo());
        usuario.setTelefono(request.telefono());

        // La contraseña normalmente se actualiza en un método aparte.

        UsuarioModel actualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(actualizado);
    }

    /* cambiar estado de usuario */
    @Override
    public UsuarioResponseDTO cambiarEstado(Long id, EstadoUsuario estado) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        usuario.setEstado(estado);

        UsuarioModel usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioActualizado);
    }

    /* cambiar contaseña (modificar al implementar spreent security) */
    @Override
    public void cambiarContrasena(Long id, String contrasenaActual, String nuevaContrasena) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        if (!usuario.getContrasena().equals(contrasenaActual)) {
            throw new RuntimeException("La contraseña actual es incorrecta.");
        }

        usuario.setContrasena(nuevaContrasena);

        usuarioRepository.save(usuario);
    }

    /* obtener usuarios por rol */
    @Override
    public List<UsuarioResponseDTO> obtenerUsuariosPorRol(Rol rol) {

        return usuarioRepository.findByRol(rol)
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    /* obtener usuarios por estado */
    @Override
    public List<UsuarioResponseDTO> obtenerUsuariosPorEstado(EstadoUsuario estado) {

        return usuarioRepository.findByEstado(estado)
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

}

