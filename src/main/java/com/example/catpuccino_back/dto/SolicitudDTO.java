package com.example.catpuccino_back.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolicitudDTO {

    private Integer id;
    private UsuarioDTO usuarioDTO;
    private GatoDTO gatoDTO;
    private String mensaje;
    private Boolean aceptada;
}
