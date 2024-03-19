package com.example.catpuccino_back.dto;

import com.example.catpuccino_back.models.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private Integer telefono;
    private String email;
    private String dni;
    private Rol rol;
    private String nombreUsuario;
    private String password;

}
