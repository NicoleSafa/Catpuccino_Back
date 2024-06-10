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


import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    //Listar Basico de Reservas
    @GetMapping(value="/reserva")
    public List<ReservaDTO> listarReserva(){
        return reservaService.listarReservas();
    }

    //Listar Reserva por ID
    @GetMapping(value = "/reserva/ultima/{id}")
    public Integer obtenerUltimaReservaUsuario(@PathVariable("id") int idUsuario) {
        return reservaService.ultimareserva(idUsuario);

    }
    //Listar Todas las Reservas de un Usuario
    @GetMapping(value="/reserva/reservas/{id}")
    public List<ReservaDTO> obtenerReservasUsuario(@PathVariable("id") int idUsuario) {
        return reservaService.obtenerReservasUsuario(idUsuario);
    }

    //Crear Reserva
    @PostMapping(value = "/reserva/crear")
    public ReservaDTO crearReserva(@RequestBody ReservaDTO reservaDTO){
        return reservaService.crearReserva(reservaDTO);
    }

    // Actualiza el estado de la Reserva
    @PostMapping("/reserva/actualizar")
    public ResponseEntity<String> actualizarEstadoReservas() {
        try {
            reservaService.actualizarReservasAusentes();
            return ResponseEntity.ok("El estado de las reservas ha sido actualizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estado de las reservas.");
        }
    }

    // Editar Reserva
    @PostMapping(value="/reserva/modificar/{id}")
    public String editar (@PathVariable("id") Integer id, @RequestBody ReservaDTO reservaDTO){
        return reservaService.editarReserva(reservaDTO, id);
    }

    // Borrar Reserva
    @DeleteMapping(value="/reserva/borrar")
    public String eliminar (@PathVariable("id") Integer id){
        return this.reservaService.borrarReserva(id);
    }

    // Metodo que comprueba disponibilidad de la fecha y la hora para hacer reserva
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

        LocalDate fechaActual = LocalDate.now();
        Date fechaUsuarioDate = Date.valueOf(fechaActual);
        LocalTime horaActualLocalTime = LocalTime.now();
        Time horaActualTime = Time.valueOf(horaActualLocalTime);


        if (time != null && fecha1 != null) {

            if (fecha1.after(fechaUsuarioDate) || fecha1.equals(fechaUsuarioDate)) {
                return reservaService.comprobarReserva(time, fecha1);
            }else {
                mensaje = "Lo sentimos pero la fecha o la hora, ya han pasado " + fecha1 + " a las " + time;
                return new Object[] {false,mensaje};
            }

        } else if (fecha1 == null){

            List<Object> fechasDisponibles = reservaService.obtenerFechasDisponibles(time);
            return fechasDisponibles.toArray(new Object[0][]);

        }else if (time == null){

            if (fecha1.after(fechaUsuarioDate) || fecha1.equals(fechaUsuarioDate)) {
                List<Object[]> result = reservaService.verificarDisponibilidad(fecha1);
                return result.toArray(new Object[0][]);
            }else {
                mensaje = "Lo sentimos pero la fecha que ha seleccionado, ya ha pasado " + fechaUsuarioDate;
                return new Object[] {mensaje};
            }

        } else {
            return new Object[] {mensaje};
        }
    }

    // Cancelación de reserva
    @PostMapping("/reserva/cancelar/{id}")
    public ResponseEntity<Reserva> cancelarReservaPorId(@PathVariable Integer id) {
        Reserva reservaCancelada = reservaService.cancelarReserva(id);
        if (reservaCancelada != null) {
            return ResponseEntity.ok(reservaCancelada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reserva/fechayhora")
    public List<ReservaDTO> reservasFechaHora(){
        return reservaService.reservasFechaHoraActual();
    }

    @GetMapping("/reserva/ultimaReserva/{idUsuario}")
    public Reserva ultimaReservaActiva (@PathVariable int idUsuario){
        return reservaService.ultimaReservaActiva(idUsuario);
    }
    @GetMapping("/reserva/quedanDiezMinutos/{idUsuario}")
    public ResponseEntity<Boolean> quedanDiezMinutosOmenosParaReserva(@PathVariable int idUsuario) {
        boolean quedanDiezMinutos = reservaService.quedanDiezMinutosOmenosParaReserva(idUsuario);
        return ResponseEntity.ok(quedanDiezMinutos);
    }



    @PutMapping("/reserva/ausentes")
    public ResponseEntity<Boolean> reservasAusentes() {
        Boolean result = reservaService.cancelarReservasHora();
        return ResponseEntity.ok(result); // Devolver true o false en el cuerpo de la respuesta
    }

    @GetMapping("/reserva/pagadasdia")
    public ResponseEntity<List<Reserva>> obtenerReservasDelDiaPagadas() {
        List<Reserva> reservasPagadasDelDia = reservaService.obtenerReservasDelDiaPagadas();
        if (reservasPagadasDelDia.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(reservasPagadasDelDia, HttpStatus.OK);
        }
    }
    }


