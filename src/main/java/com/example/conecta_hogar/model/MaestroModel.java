package com.example.conecta_hogar.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "maestro")
@PrimaryKeyJoinColumn(name = "id_usuario") // Une la clave primaria de maestro con la de usuario
@Getter
@Setter
@NoArgsConstructor
public class MaestroModel extends UsuarioModel {

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 100, message = "La especialidad no puede superar los 100 caracteres")
    @Column(nullable = false)
    private String especialidad; // Ej: "Electricista", "Gasfiter"

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @NotNull(message = "El estado de actividad es obligatorio")
    @Column(nullable = false)
    private Boolean activo = true;

    // --- NUEVOS CAMPOS PARA EL SISTEMA DE VALORACIÓN ---

    @NotNull(message = "El contador de 'Me gusta' es obligatorio")
    @Min(value = 0, message = "El valor mínimo de 'Me gusta' debe ser 0")
    @Column(name = "me_gusta", nullable = false)
    private Integer meGusta = 0; // inicia en 0 por defecto

    @NotNull(message = "El contador de 'No me gusta' es obligatorio")
    @Min(value = 0, message = "El valor mínimo de 'No me gusta' debe ser 0")
    @Column(name = "no_gusta", nullable = false)
    private Integer noMeGusta = 0; // inicia en 0 por defecto

    // --- PASO 3: RELACIÓN CON LA TABLA INTERMEDIA (ESPECIALIDADES) ---

    @OneToMany(
            mappedBy = "maestro",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MaestroEspecialidadModel> especialidades = new ArrayList<>();
}