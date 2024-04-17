package com.example.catpuccino_back.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
public class ReservaDTO {
    private Integer id;
    private String nombre_reserva;
    private String telefono;
    private Date fecha;
    private Time hora;
    private Integer numeroPersonas;
    private Boolean reserva_activa;
    private Boolean pagado;
    private Double total;
    private UsuarioDTO usuarioDTO;

}
