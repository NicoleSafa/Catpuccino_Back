package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping(value="/reserva")
    public List<ReservaDTO> listarReserva(){
        return reservaService.listarReservas();
    }

    @GetMapping(value = "/reserva/ultima/{id}")
    public Integer obtenerUltimaReservaUsuario(@PathVariable("id") int idUsuario) {
        return reservaService.ultimareserva(idUsuario);

    }

    @GetMapping("/comprobarReserva")
    public Object[] comprobarReserva(@RequestParam String hora, @RequestParam String fecha) {
        String mensaje = "Por favor seleccione todos los campos";

        SimpleDateFormat horita = new SimpleDateFormat("HH:mm");
        Time time = null;
        try {
            java.util.Date parsedDate = horita.parse(hora);
            time = new Time(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fecha1 = null;
        try {
            // Parsea la cadena de fecha a un objeto java.util.Date
            java.util.Date parsedDate = fechita.parse(fecha);

            // Convierte java.util.Date a java.sql.Date
            fecha1 = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (time != null && fecha1 != null) {
            return reservaService.comprobarReserva(time, fecha1);
        } else if (fecha1 == null){
            List<Object> fechasDisponibles = reservaService.obtenerFechasDisponibles(time);
            return fechasDisponibles.toArray(new Object[0][]);

        }else if (time == null){
            List<Object[]> result = reservaService.verificarDisponibilidad(fecha1);
            return result.toArray(new Object[0][]);
        } else {
            return new Object[] {mensaje};
        }
    }

}
