package com.example.catpuccino_back.dto;

import com.example.catpuccino_back.models.enums.Tipo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Tipo tipo;
}