package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.EspecialidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadModel, Long> {

    boolean existsByNombre(String nombre);

    Optional<EspecialidadModel> findByNombre(String nombre);

}