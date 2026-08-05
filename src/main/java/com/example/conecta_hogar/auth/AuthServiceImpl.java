package com.example.conecta_hogar.auth;

import com.example.conecta_hogar.model.MaestroModel;
import com.example.conecta_hogar.model.Rol;
import com.example.conecta_hogar.model.UsuarioModel;
import com.example.conecta_hogar.repository.UsuarioRepository;
import com.example.conecta_hogar.security.CustomUserDetails;
import com.example.conecta_hogar.security.CustomUserDetailsService;
import com.example.conecta_hogar.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        // 1. Autenticar usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.correo(),
                        request.contrasena()
                )
        );

        // 2. Obtener datos del usuario
        CustomUserDetails userDetails =
                (CustomUserDetails) userDetailsService.loadUserByUsername(request.correo());

        // 3. Generar JWT
        String jwtToken = jwtService.generateToken(userDetails);

        // 4. Obtener el rol
        String rol = userDetails.getUsuario().getRol().name();

        // 5. Respuesta
        return LoginResponseDTO.builder()
                .mensaje("Inicio de sesión exitoso")
                .token(jwtToken)
                .rol(rol)
                .build();
    }
    @Override
    public LoginResponseDTO register(RegisterRequestDTO request) {

        // 1. Validar si el correo ya existe
        if (usuarioRepository.existsByCorreo(request.correo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // 2. Obtener la contraseña enviada desde el DTO
        String rawPassword = request.password();
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        // 3. Encriptar la contraseña
        String passwordEncriptada = passwordEncoder.encode(rawPassword);

        // 4. Determinar si el registro es de un profesional (MAESTRO) o un cliente
        boolean esProfesional = request.tipoUsuario() != null
                && (request.tipoUsuario().toUpperCase().contains("PROFESIONAL")
                || request.tipoUsuario().toUpperCase().contains("MAESTRO"));

        // 5. Crear el objeto correspondiente y asignar campos reales (incluyendo dirección)
        // 👉 IMPORTANTE: si es profesional, se crea un MaestroModel (subclase de UsuarioModel)
        //    para que también quede la fila en la tabla "maestro" y aparezca en /maestros.
        UsuarioModel nuevoUsuario;

        if (esProfesional) {
            MaestroModel nuevoMaestro = new MaestroModel();

            // La especialidad es obligatoria en la tabla maestro (@NotBlank)
            String especialidad = (request.especialidades() != null && !request.especialidades().isEmpty())
                    ? request.especialidades().get(0)
                    : "Sin especialidad";
            nuevoMaestro.setEspecialidad(especialidad);
            nuevoMaestro.setActivo(true);

            nuevoUsuario = nuevoMaestro;
        } else {
            nuevoUsuario = new UsuarioModel();
        }

        nuevoUsuario.setNombre(request.nombre());
        nuevoUsuario.setApellido(request.apellido());
        nuevoUsuario.setRut(request.rut());
        nuevoUsuario.setTelefono(request.telefono());
        nuevoUsuario.setCorreo(request.correo());
        nuevoUsuario.setDireccion(request.direccion()); // 👈 Mapea la dirección recibida
        nuevoUsuario.setContrasena(passwordEncriptada);
        nuevoUsuario.setRol(esProfesional ? Rol.MAESTRO : Rol.CLIENTE);

        // 6. Guardar en Base de Datos
        usuarioRepository.save(nuevoUsuario);

        // 7. Cargar UserDetails y generar Token JWT
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(nuevoUsuario.getCorreo());
        String jwtToken = jwtService.generateToken(userDetails);

        // 8. Retornar respuesta con Token
        return LoginResponseDTO.builder()
                .mensaje("Registro exitoso")
                .token(jwtToken)
                .build();
    }
}