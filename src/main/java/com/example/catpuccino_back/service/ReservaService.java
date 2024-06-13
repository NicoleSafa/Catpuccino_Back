package com.example.catpuccino_back.service;


import com.example.catpuccino_back.converter.ReservaMapper;
import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.models.enums.EstadoReserva;
import com.example.catpuccino_back.repository.ConsumicionRepository;
import com.example.catpuccino_back.repository.ReservaRepository;
import com.example.catpuccino_back.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.*;
import java.util.List;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Getter
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ConsumicionRepository consumicionRepository;

    //Metodo para ReservaService
    public Reserva getByIdReserva(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    //LISTAR
    public List<ReservaDTO> listarReservas() {
        return reservaMapper.toDTO(reservaRepository.findAll());
    }

    // Metodo que verifica la disponibilidad de la reserva
    // Este metodo forma parte del comprobar reserva
    public Object[] comprobarReserva(Time hora, Date fecha) {
        boolean exito = true;
        String mensaje = "Hay reserva disponible";
        LocalTime horaActualLocalTime = LocalTime.now();
        Time horaActualTime = Time.valueOf(horaActualLocalTime);
        LocalDate fechaActual = LocalDate.now();
        Date fechaUsuarioDate = Date.valueOf(fechaActual);

        if (hora != null && fecha != null) {
            if ((hora.after(horaActualTime) || hora.equals(horaActualTime)) && fecha.equals(fechaUsuarioDate)) {
                int numReserva = reservaRepository.disponibilidadHoras(hora,fecha);
                if (numReserva < 5) {
                    return new Object[] {exito, mensaje};
                } else {
                    exito = false;
                    mensaje = "Lo sentimos pero no hay reservas disponibles para esta hora y dia";
                    return new Object[] {exito, mensaje};
                }
            } else if (fecha.after(fechaUsuarioDate)) {
                int numReserva = reservaRepository.disponibilidadHoras(hora,fecha);
                if (numReserva < 5) {
                    return new Object[] {exito, mensaje};
                } else {
                    exito = false;
                    mensaje = "Lo sentimos pero no hay reservas disponibles para esta hora y dia";
                    return new Object[] {exito, mensaje};
                }
            } else {
                exito = false;
                mensaje = "Lo sentimos pero la hora que selecciono, ya ha pasado " + hora;
                return new Object[] {exito, mensaje};
            }


        } else {
            exito = false;
            mensaje = "Por favor seleccione todos los campos";
            return new Object[]{exito, mensaje};
        }
    }

    // Método para verificar disponibilidad de reservas para cada hora dentro del horario especificado
    // Este metodo forma parte del comprobar reserva
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
    // Este metodo forma parte del comprobar reserva
    private boolean esHoraDisponibleEnBD(Date fecha, int hora) {
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
    // Este metodo forma parte del comprobar reserva
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
    // Este metodo forma parte del comprobar reserva
    private boolean esFechaDisponibleEnBD(Date fecha, Time hora) {
        int count = reservaRepository.disponibilidadHoras(hora,fecha);
        return count < 5;
    }


    //PARA EL FILTRO DE LA ULTIMA RESERVA
    public Integer ultimareserva(int idUsuario) {
        return reservaRepository.ultimareservausuario(idUsuario);
    }

    //Crear Reserva
    public ReservaDTO crearReserva(ReservaDTO reservaDTO){
        return reservaMapper.toDTO(reservaRepository.save(reservaMapper.toEntity(reservaDTO)));
    }


    //Las reservas del usuario
    public List<ReservaDTO> obtenerReservasUsuario(@Param("idUsuario") int idUsuario) {
        List<Reserva> reservas = reservaRepository.reservasUsuario(idUsuario);
        List<ReservaDTO> reservasDTO = reservaMapper.toDTO(reservas);
        return reservasDTO;
    }


    // Método para actualizar el estado de las reservas
    public void actualizarReservasAusentes(){
        LocalTime horaLimite = LocalTime.now().minusHours(1).minusMinutes(20); // Una hora y veinte minutos atrás

        List<Reserva> reservas = reservaRepository.findAll();
        for (Reserva cosa : reservas) {
            Time horaReservaSql = cosa.getHora();
            LocalTime horaReserva = horaReservaSql.toLocalTime();
            SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd"); // le doy el formato
            java.util.Date fecha1 = null; //creo una variable nueva del tipo de dato
            try {
                // Parsea la cadena de fecha a un objeto java.util.Date
                java.util.Date parsedDate = fechita.parse(String.valueOf(cosa.getFecha()));

                // Convierte java.util.Date a java.sql.Date
                fecha1 = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Boolean esFechaActual = ai.equals(socorro);
            Boolean haPasadoHoraLimite = horaReserva.compareTo(horaLimite) <= 0;
            Boolean estaPagado = cosa.getPagado().equals(Boolean.FALSE);
            // Convertir Date a Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha1);
            LocalDate pasarFecha1 = LocalDate.of(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
            Boolean laFecha = pasarFecha1.equals(LocalDate.now());

                if (haPasadoHoraLimite && estaPagado && laFecha) {
                    cosa.setReserva_activa(Boolean.FALSE);
                    cosa.setEstadoReserva(EstadoReserva.AUSENTE);

                    reservaRepository.save(cosa);
                }

        }


    }

    //borrar
    public String borrarReserva (Integer id){
        if (id==null){
            return ("No se ha encontrado la reserva");
        }else{
            reservaRepository.deleteById(id);
            return ("Reserva eliminada");
        }
    }

    //editar
    public String editarReserva(ReservaDTO reservaDTO, Integer id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva == null) {
            return "Reserva no encontrada";
        } else {
            // Actualizar los campos que siempre se deben actualizar
            reserva.setNombre_reserva(reservaDTO.getNombre_reserva());
            reserva.setTelefono(reservaDTO.getTelefono());
            reserva.setNumeroPersonas(reservaDTO.getNumeroPersonas());

            // Verificar disponibilidad de la nueva fecha y hora
            Object[] resultado = comprobarReserva(reservaDTO.getHora(), reservaDTO.getFecha());
            boolean exito = (boolean) resultado[0];
            String mensaje = (String) resultado[1];

            if (exito) {
                // Si hay disponibilidad, actualizar también la fecha y hora
                reserva.setFecha(reservaDTO.getFecha());
                reserva.setHora(reservaDTO.getHora());
                mensaje = "true";
            } else {
                mensaje = "false";
            }

            reservaRepository.save(reserva);
            return mensaje;
        }
    }
    // Cancelar reserva
    public Reserva cancelarReserva (Integer reservaId){
        Reserva reserva = reservaRepository.findById(reservaId).orElse(null);
        if (reserva != null && reserva.getEstadoReserva().equals(EstadoReserva.ACTIVO)){
            reserva.setEstadoReserva(EstadoReserva.CANCELADO);
            reserva.setReserva_activa(false);
            reservaRepository.save(reserva);
        }
        return reserva;
    }

    //Ultima reserva activa del usuario
    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);
    public Reserva ultimaReservaActiva (int idUsuario){

        Integer idReserva = ultimareserva(idUsuario);
        if (idReserva == null) {
            logger.warn("No se encontró ninguna reserva activa para el usuario con ID: ", idUsuario);
            return null;
        }
        Reserva reserva = getByIdReserva(idReserva);
        return reserva;
    }


    public boolean quedanDiezMinutosOmenosParaReserva(int idUsuario) {
        Reserva reservaActiva = ultimaReservaActiva(idUsuario);

        if (reservaActiva == null) {
            // No hay reserva activa para el usuario
            return false;
        }
        // Obtener la fecha y hora de la reserva
        LocalDateTime fechaHoraReserva = LocalDateTime.of(
                reservaActiva.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                reservaActiva.getHora().toLocalTime()
        );
        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        // Calcular la diferencia en minutos entre la hora actual y la hora de la reserva
        long diferenciaMinutos = ChronoUnit.MINUTES.between(ahora, fechaHoraReserva);
        // Devolver true si quedan 10 minutos o menos para la reserva
        return (diferenciaMinutos <= 10 && diferenciaMinutos >= 0) || (ahora.isAfter(fechaHoraReserva) && ahora.isBefore(fechaHoraReserva.plusHours(1)));
    }
    public List<ReservaDTO> reservasFechaHoraActual() {
        List<Reserva> reservas = reservaRepository.findAll();
        List<Reserva> reservasAMostrar = new ArrayList<>();

        LocalTime hora = LocalTime.now();
        for (Reserva cosa : reservas) {
            Time horaReservaSql = cosa.getHora();
            LocalTime horaReserva1 = cosa.getHora().toLocalTime();
            LocalTime horaReserva = horaReservaSql.toLocalTime();
            SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd"); // le doy el formato
            java.util.Date fecha1 = null; //creo una variable nueva del tipo de dato
            try {
                // Parsea la cadena de fecha a un objeto java.util.Date
                java.util.Date parsedDate = fechita.parse(String.valueOf(cosa.getFecha()));

                // Convierte java.util.Date a java.sql.Date
                fecha1 = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean horaOk = horaReserva.getHour() == hora.getHour();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha1);
            LocalDate pasarFecha1 = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            Boolean fechaOk = pasarFecha1.equals(LocalDate.now());
            Boolean activo = cosa.getEstadoReserva().equals(EstadoReserva.ACTIVO);
            if (horaOk && fechaOk && activo) {

                reservasAMostrar.add(cosa);
            }
        }
        List<ReservaDTO> reservasDTO = reservaMapper.toDTO(reservasAMostrar);
        return reservasDTO;
    }

    public Boolean cancelarReservasHora() {
        List<ReservaDTO> reservas = reservasFechaHoraActual();
        for (ReservaDTO cosa : reservas) {
            Boolean comprobante = consumicionRepository.existenConsumicionesReserva(cosa.getId());
            if (comprobante == false) {
                cosa.setEstadoReserva(EstadoReserva.AUSENTE);
                cosa.setReserva_activa(false);
                Reserva cosa1 = reservaMapper.toEntity(cosa);
                reservaRepository.save(cosa1);
                return true;

            }
        }return false;

    }
    public List<ReservaDTO> obtenerReservasDelDiaPagadas() {
       List<Reserva> reservas = reservaRepository.reservasDelDiaPagadas();
       List<ReservaDTO> reservasDTO = reservaMapper.toDTO(reservas);
       return reservasDTO;
    }
}



