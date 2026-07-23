package com.example.conecta_hogar.auth;


import lombok.Builder;

@Builder
public record LoginResponseDTO(


    String token,

    String mensaje

){}