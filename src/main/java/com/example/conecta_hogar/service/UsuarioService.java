package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.UsuarioRequestDTO;
import com.example.conecta_hogar.dto.UsuarioResponseDTO;
import com.example.conecta_hogar.model.EstadoUsuario;
import com.example.conecta_hogar.model.Rol;

import java.util.List;

public interface UsuarioService {

    /*AUÍ SOLO SE DECLARAN LAS FUNCIONES QUE PODRÁ RALIZAR EL SISTEMA*/

    /* Crear usuario */
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request);

    /* Obtener todos los usuarios */
    List<UsuarioResponseDTO> obtenerUsuarios();

    /* Obtener usuario por id */
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);

    /* Actualizar usuario */
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO request);


    /*cambiar estado de usuario*/
    UsuarioResponseDTO cambiarEstado(Long id, EstadoUsuario estado);

    /*cambiar contraseña*/
    void cambiarContrasena(Long id, String contrasenaActual, String nuevaContrasena);

    /*obtener usuarios por rol*/
    List<UsuarioResponseDTO> obtenerUsuariosPorRol(Rol rol);

    /*obtener usuarios por estado*/
    List<UsuarioResponseDTO> obtenerUsuariosPorEstado(EstadoUsuario estado);

}