package com.example.catpuccino_back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Builder;



@Data
@Builder
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer precio;
}