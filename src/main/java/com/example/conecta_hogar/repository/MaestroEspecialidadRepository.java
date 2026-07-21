package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.MaestroEspecialidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaestroEspecialidadRepository extends JpaRepository<MaestroEspecialidadModel, Long> {

    // Método para obtener todas las especialidades asociadas a un maestro por su ID
    List<MaestroEspecialidadModel> findByMaestroIdUsuario(Long idUsuario);
}
