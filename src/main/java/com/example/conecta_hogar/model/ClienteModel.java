package com.example.conecta_hogar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id_usuario") // Une la clave primaria de cliente con la de usuario
@Getter
@Setter
@NoArgsConstructor
public class ClienteModel extends UsuarioModel {

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    @Column(nullable = false)
    private String direccion;
}