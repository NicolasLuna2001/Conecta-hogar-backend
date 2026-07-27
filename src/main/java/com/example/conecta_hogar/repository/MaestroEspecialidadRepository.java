package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.MaestroEspecialidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaestroEspecialidadRepository
        extends JpaRepository<MaestroEspecialidadModel, Long> {

    List<MaestroEspecialidadModel> findByMaestroIdUsuario(Long idMaestro);

    List<MaestroEspecialidadModel>
    findByEspecialidadIdEspecialidad(Long idEspecialidad);

    boolean existsByMaestroIdUsuarioAndEspecialidadIdEspecialidad(
            Long idMaestro,
            Long idEspecialidad
    );

    void deleteByMaestroIdUsuarioAndEspecialidadIdEspecialidad(
            Long idMaestro,
            Long idEspecialidad
    );
}