package com.example.catpuccino_back.dto;

import com.example.catpuccino_back.models.Usuario;
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
    private Boolean reserva_activa;
    private Boolean pagado;
    private Double total;
    private Usuario id_usuario;

}
