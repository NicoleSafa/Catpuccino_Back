package com.example.catpuccino_back.service;


import com.example.catpuccino_back.converter.ReservaMapper;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;






@Getter
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    //Metodo para ReservaService
    public Reserva getByIdReserva(Integer id){
        return reservaRepository.findById(id).orElse(null);
    }

    //LISTAR
    public List<ReservaDTO> listarReservas() {return reservaMapper.toDTO(reservaRepository.findAll());}


    public Object[] comprobarReserva(Time hora, Date fecha) {
        boolean exito = true;
        String mensaje = "Hay reserva disponible";

        if (hora != null && fecha != null) {
            int numReserva = reservaRepository.disponibilidadHoras(hora,fecha);
            if (numReserva < 5) {
                return new Object[] {exito, mensaje};
            } else {
                exito = false;
                mensaje = "Lo sentimos pero no hay reservas disponibles para esta hora";
                return new Object[] {exito, mensaje};
            }

        } else {
        exito = false;
        mensaje = "Por favor seleccione todos los campos";
        return new Object[] {exito, mensaje};
        }
    }

    // Método para verificar disponibilidad de reservas para cada hora dentro del horario especificado
    public List<Object[]> verificarDisponibilidad(Date fecha) {
        List<Object[]> disponibilidadPorHora = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        // Defino el horario de trabajo
        int horaInicioManana = 9;
        int horaFinManana = 15;
        int horaInicioTarde = 16;
        int horaFinTarde = 20;


        for (int hora = horaInicioManana; hora <= horaFinManana || hora <= horaFinTarde; hora++) {
            if ((hora >= horaInicioManana && hora < horaFinManana) || (hora >= horaInicioTarde && hora < horaFinTarde)) {
                boolean disponible = esHoraDisponibleEnBD(fecha, hora);
                String horaFormatted = String.format("%02d:00", hora);

                Object[] disponibilidadHora = new Object[2];
                disponibilidadHora[0] = horaFormatted;
                disponibilidadHora[1] = disponible;
                disponibilidadPorHora.add(disponibilidadHora);
            }
        }
        return disponibilidadPorHora;
    }

    // Método para verificar si una hora específica está disponible consultando la base de datos
    private boolean esHoraDisponibleEnBD(Date fecha, int hora) {
        // Convertir hora a formato adecuado para la base de datos (hh:mm:ss)
        String horaFormatted = String.format("%02d:00:00", hora);

        SimpleDateFormat horita = new SimpleDateFormat("HH:mm:ss");
        Time time = null;
        try {
            java.util.Date parsedDate = horita.parse(horaFormatted);
            time = new Time(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int count = reservaRepository.disponibilidadHoras(time,fecha);
        return count < 5;
    }




    // Método para obtener las fechas disponibles para una hora específica en los próximos 7 días laborables
    public List<Object> obtenerFechasDisponibles(Time horaEspecifica) {
        List<Object> fechasDisponibles = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()); // Obtener la fecha actual

        // Iterar sobre los próximos 7 días
        for (int i = 0; i < 7; i++) {
            // Verificar si el día actual es laborable (de lunes a viernes)
            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY) {
                // Convertir la fecha actual a java.sql.Date
                java.sql.Date fechaSql = new java.sql.Date(calendar.getTimeInMillis());
                if (esFechaDisponibleEnBD(fechaSql, horaEspecifica) == true) {

                    Object[] disponibilidadHora = new Object[2];
                    disponibilidadHora[0] = horaEspecifica;
                    disponibilidadHora[1] = new Date(fechaSql.getTime());
                    fechasDisponibles.add(disponibilidadHora);
                }else {
                    i--;
                }
            }else {
                i--;
            }
            // Avanzar al siguiente día
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return fechasDisponibles;
    }

    // Método para verificar si hay disponibilidad para una hora específica en una fecha específica
    private boolean esFechaDisponibleEnBD(Date fecha, Time hora) {
        int count = reservaRepository.disponibilidadHoras(hora,fecha);
        return count < 5;
    }






    //PARA EL FILTRO DE LA ULTIMA RESERVA
    public Integer ultimareserva(int id) {
        return reservaRepository.ultimareservausuario(id);
    }
}
