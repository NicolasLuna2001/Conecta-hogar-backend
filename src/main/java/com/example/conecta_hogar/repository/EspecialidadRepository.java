package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.EspecialidadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadModel, Long> {
}