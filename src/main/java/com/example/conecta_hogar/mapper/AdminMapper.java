package com.example.conecta_hogar.mapper;

import com.example.conecta_hogar.dto.AdminRequestDTO;
import com.example.conecta_hogar.dto.AdminResponseDTO;
import com.example.conecta_hogar.model.AdminModel;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {


    /* RequestDTO -> Model */
    public AdminModel toModel(AdminRequestDTO request) {

        AdminModel admin = new AdminModel();

        admin.setNombre(request.nombre());
        admin.setApellido(request.apellido());
        admin.setCorreo(request.correo());
        admin.setContrasena(request.contrasena());
        admin.setTelefono(request.telefono());

        return admin;
    }

    /* Model -> ResponseDTO */
    public AdminResponseDTO toDTO(AdminModel admin) {

        return AdminResponseDTO.builder()
                .nombre(admin.getNombre())
                .apellido(admin.getApellido())
                .correo(admin.getCorreo())
                .telefono(admin.getTelefono())
                .rol(admin.getRol())
                .fechaRegistro(admin.getFechaRegistro())
                .build();
    }
}
