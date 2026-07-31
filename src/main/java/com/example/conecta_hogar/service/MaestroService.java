package com.example.conecta_hogar.service;

import com.example.conecta_hogar.dto.MaestroRequestDTO;
import com.example.conecta_hogar.dto.MaestroResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MaestroService {
    MaestroResponseDTO crearMaestro(MaestroRequestDTO request);

    MaestroResponseDTO crearMaestroConFoto(MaestroRequestDTO request, MultipartFile foto);

    // Nuevo método para actualizar la foto de un maestro existente
    MaestroResponseDTO actualizarFotoMaestro(Long id, MultipartFile foto);

    List<MaestroResponseDTO> obtenerMaestros();
    MaestroResponseDTO maestroById(Long id);
    MaestroResponseDTO actualizarMaestro(Long id, MaestroRequestDTO request);
    void eliminarMaestro(Long id);

    // Métodos para interactuar con la valoración
    MaestroResponseDTO darMeGusta(Long id);
    MaestroResponseDTO darNoMeGusta(Long id);
    List<MaestroResponseDTO> obtenerTopMaestros();
}