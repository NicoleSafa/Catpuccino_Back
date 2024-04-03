package com.example.catpuccino_back.dto;
import com.example.catpuccino_back.models.enums.Raza;
import com.example.catpuccino_back.models.enums.Sexo;
import com.example.catpuccino_back.models.enums.Tamanyo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatoDTO {
    private Integer id;
    private String nombre;
    private String imagen;
    private Raza raza;
    private Tamanyo tamanyo;
    private Sexo sexo;
    private String descripcionCorta;
    private Boolean disponible;
    private Boolean vacunacionCompleta;
    private String descripcionLarga;
    private Boolean chip;
    private Boolean necesidadesEspeciales;



}
