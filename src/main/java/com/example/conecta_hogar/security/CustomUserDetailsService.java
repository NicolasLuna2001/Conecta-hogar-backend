package com.example.conecta_hogar.security;

import com.example.conecta_hogar.model.UsuarioModel;
import com.example.conecta_hogar.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        UsuarioModel usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        // Retornamos nuestra implementación CustomUserDetails
        // Las autoridades (roles) y el estado se gestionan internamente en dicha clase.
        return new CustomUserDetails(usuario);
    }
}