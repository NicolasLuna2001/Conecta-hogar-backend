package com.example.conecta_hogar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "maestro_especialidad")
@Getter
@Setter
@NoArgsConstructor
public class MaestroEspecialidadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maestro_especialidad")
    private Long id;

    // Conexión N:1 hacia el Maestro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private MaestroModel maestro;

    // Conexión N:1 hacia la Especialidad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad", nullable = false)
    private EspecialidadModel especialidad;

    // Datos extra opcionales que enriquecen la relación intermedia:
    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    @Column(name = "es_principal")
    private Boolean esPrincipal = false;
}