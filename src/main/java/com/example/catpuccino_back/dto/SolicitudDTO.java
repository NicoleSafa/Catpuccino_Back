package com.example.catpuccino_back.dto;


import com.example.catpuccino_back.models.enums.EstadoSolicitud;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolicitudDTO {

    private Integer id;
    private UsuarioDTO usuarioDTO;
    private GatoDTO gatoDTO;
    private String titulo;
    private String mensaje;
    private EstadoSolicitud estadoSolicitud;
}
