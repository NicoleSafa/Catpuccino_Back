package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.repository.ReservaRepository;
import com.example.catpuccino_back.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    //LAS RESERVAS DE CADA USUARIO
    @GetMapping(value="/reserva/reservas/{id}")
    public List<ReservaDTO> obtenerReservasUsuario(@PathVariable("id") int idUsuario) {
        return reservaService.obtenerReservasUsuario(idUsuario);

    }

    @GetMapping("/comprobarReserva")
    public Object[] comprobarReserva(@RequestParam String hora, @RequestParam String fecha) {
        String mensaje = "Por favor seleccione todos los campos";

        SimpleDateFormat horita = new SimpleDateFormat("HH:mm:ss");
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
    @PostMapping(value = "/reserva/crear")
    public ReservaDTO crearReserva(@RequestBody ReservaDTO reservaDTO){
        return reservaService.crearReserva(reservaDTO);
    }

    //cambiar el estado
    @PostMapping("/reserva/actualizar")
    public ResponseEntity<String> actualizarEstadoReservas() {
        try {
            reservaService.actualizarReservasAusentes();
            return ResponseEntity.ok("El estado de las reservas ha sido actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estado de las reservas.");
        }
    }

    //modificar
    @PostMapping(value="/reserva/modificar/{id}")
    public String editar (@PathVariable("id") Integer id, @RequestBody ReservaDTO reservaDTO){
        return reservaService.editarReserva(reservaDTO, id);
    }

    //borrar
    @DeleteMapping(value="/reserva/borrar")
    public String eliminar (@PathVariable("id") Integer id){
        return this.reservaService.borrarReserva(id);
    }

    @PostMapping("/reserva/cancelar/{id}")
    public ResponseEntity<Reserva> cancelarReservaPorId(@PathVariable Integer id) {
        Reserva reservaCancelada = reservaService.cancelarReserva(id);
        if (reservaCancelada != null) {
            return ResponseEntity.ok(reservaCancelada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
