package com.example.conecta_hogar.security;

import com.example.conecta_hogar.model.UsuarioModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UsuarioModel usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolNombre = usuario.getRol().name();
        String rolSinPrefix = rolNombre.replace("ROLE_", "");
        String rolConPrefix = "ROLE_" + rolSinPrefix;

        // Mapea tanto "ADMIN" como "ROLE_ADMIN" para compatibilidad total
        return List.of(
                new SimpleGrantedAuthority(rolSinPrefix),
                new SimpleGrantedAuthority(rolConPrefix)
        );
    }

    /*que contraseña tiene*/
    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    /*cual es username*/
    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    /*la cuenta ha expirado*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*la cuenta está bloqueada*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*está habilitado*/
    @Override
    public boolean isEnabled() {
        return usuario.getEstado() != null && usuario.getEstado().name().equals("ACTIVO");
    }
}