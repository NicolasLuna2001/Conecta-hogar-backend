package com.example.conecta_hogar.repository;

import com.example.conecta_hogar.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long > {

    /*AQUÍ ME COMUNICO Y HAGO CONSULTAS A LA BD*/



}
