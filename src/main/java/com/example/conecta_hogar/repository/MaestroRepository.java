package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.MaestroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaestroRepository extends JpaRepository<MaestroModel, Long> {

    // Query Method para buscar maestros por especialidad ignorando mayúsculas/minúsculas
    List<MaestroModel> findByEspecialidadIgnoreCase(String especialidad);

    // Trae el "Top 5" de maestros con más "Me gusta" (¡ideal para el Home del Front!)
    List<MaestroModel> findTop5ByOrderByMeGustaDesc();
}