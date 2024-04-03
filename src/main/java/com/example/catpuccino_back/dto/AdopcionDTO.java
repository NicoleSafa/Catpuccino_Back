package com.example.catpuccino_back.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
//@Builder
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdopcionDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private GatoDTO gatoDTO;
    private Date fecha;
}
