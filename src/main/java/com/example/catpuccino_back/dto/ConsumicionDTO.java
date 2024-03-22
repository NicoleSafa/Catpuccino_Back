package com.example.catpuccino_back.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumicionDTO {
    private Integer id;
    private ReservaDTO reservaDTO;
    private ProductoDTO productoDTO;
    private Integer cantidad;


}
