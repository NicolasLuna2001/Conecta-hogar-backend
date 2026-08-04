package com.example.conecta_hogar.auth;

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

        // 1. Autenticamos el correo y contraseña con Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.correo(),
                        request.contrasena()
                )
        );

        // 2. Cargamos la información del usuario
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(request.correo());

        // 3. Generamos el token JWT real
        String jwtToken = jwtService.generateToken(userDetails);

        // 4. Retornamos la respuesta construida con el token generado
        return LoginResponseDTO.builder()
                .mensaje("Inicio de sesión exitoso")
                .token(jwtToken)
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

        // 4. Crear el objeto UsuarioModel y asignar campos reales (incluyendo dirección)
        UsuarioModel nuevoUsuario = new UsuarioModel();
        nuevoUsuario.setNombre(request.nombre());
        nuevoUsuario.setApellido(request.apellido());
        nuevoUsuario.setRut(request.rut());
        nuevoUsuario.setTelefono(request.telefono());
        nuevoUsuario.setCorreo(request.correo());
        nuevoUsuario.setDireccion(request.direccion()); // 👈 Mapea la dirección recibida
        nuevoUsuario.setContrasena(passwordEncriptada);

        // 5. Manejo del tipo de usuario / Rol
        if (request.tipoUsuario() != null) {
            String tipo = request.tipoUsuario().toUpperCase();
            if (tipo.contains("PROFESIONAL") || tipo.contains("MAESTRO")) {
                nuevoUsuario.setRol(Rol.MAESTRO);
            } else {
                nuevoUsuario.setRol(Rol.CLIENTE);
            }
        }

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