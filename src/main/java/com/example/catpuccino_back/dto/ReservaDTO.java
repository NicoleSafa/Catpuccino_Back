package com.example.catpuccino_back.dto;

import com.example.catpuccino_back.models.enums.EstadoReserva;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
public class ReservaDTO {
    private Integer id;
    private String nombre_reserva;
    private Integer telefono;
    private Date fecha;
    private Time hora;
    private Integer numeroPersonas;
    private EstadoReserva estadoReserva;
    private Boolean reserva_activa;
    private Boolean pagado;
    private Double total;
    private UsuarioDTO usuarioDTO;

}
