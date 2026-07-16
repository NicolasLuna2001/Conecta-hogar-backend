package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.EstadoUsuario;
import com.example.conecta_hogar.model.Rol;
import com.example.conecta_hogar.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long > {

    /*AQUÍ ME COMUNICO Y HAGO CONSULTAS A LA BD (no van reglas de negocio)*/

    /* Buscar usuario por correo (Login) */
    Optional<UsuarioModel> findByCorreo(String correo);

    /* Verificar si el correo ya existe */
    boolean existsByCorreo(String correo);

    /* Buscar por teléfono */
    Optional<UsuarioModel> findByTelefono(String telefono);

    /* Verificar si el teléfono ya está registrado */
    boolean existsByTelefono(String telefono);

    /* Obtener todos los usuarios de un rol */
    List<UsuarioModel> findByRol(Rol rol);

    /* Buscar por nombre */
    List<UsuarioModel> findByNombreContainingIgnoreCase(String nombre);

    /* Buscar por apellido */
    List<UsuarioModel> findByApellidoContainingIgnoreCase(String apellido);

    /*buscar usuarios según su estado*/
    List<UsuarioModel> findByEstado(EstadoUsuario estado);

    /* Buscar por nombre y apellido */
    List<UsuarioModel> findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(
            String nombre, String apellido);

}
