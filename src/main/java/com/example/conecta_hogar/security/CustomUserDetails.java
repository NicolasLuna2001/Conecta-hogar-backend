package com.example.conecta_hogar.security;

import com.example.conecta_hogar.model.UsuarioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UsuarioModel usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
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
    /*la cuenta a espirado*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*la cuanta está bloqueda*/
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
        return usuario.getEstado().name().equals("ACTIVO");
    }

}