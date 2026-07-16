package com.example.conecta_hogar.mapper;
import com.example.conecta_hogar.dto.UsuarioRequestDTO;
import com.example.conecta_hogar.dto.UsuarioResponseDTO;
import com.example.conecta_hogar.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    /*se transforma de RquestDTO a MODEL*/
    public UsuarioModel toModel(UsuarioRequestDTO request) {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setCorreo(request.correo());
        usuario.setContrasena(request.contrasena());
        usuario.setTelefono(request.telefono());

        return usuario;
    }

    /*se transforma de MODEL a ResponseDTO*/
    public UsuarioResponseDTO toDTO(UsuarioModel usuario){

        return UsuarioResponseDTO.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }


}
