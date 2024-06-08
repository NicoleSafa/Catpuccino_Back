package com.example.catpuccino_back.repository;


import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    //Filtro de comprobación de reserva disponible con la fecha y la hora
    @Query(value = "SELECT COUNT(*) FROM catpuccino.reserva r WHERE r.fecha = :fecha AND r.hora BETWEEN :hora AND (CAST(:hora AS TIME) + INTERVAL '59 minutes') AND r.reserva_activa = true", nativeQuery = true)
    int disponibilidadHoras(Time hora, Date fecha);

    //TE PONE LA ÚLTIMA RESERVA LIGADA AL USUARIO

    @Query(value = "SELECT r.id " +
            "FROM catpuccino.reserva r " +
            "JOIN catpuccino.usuario u ON u.id = r.id_usuario " +
            "WHERE r.reserva_activa = true " +
            "AND u.id = :idUsuario " +
            "AND r.fecha = CURRENT_DATE", nativeQuery = true)
    Integer ultimareservausuario(@Param("idUsuario") int idUsuario);




    @Query(value = "select * from catpuccino.reserva where id_usuario = :idUsuario", nativeQuery = true)
    List<Reserva> reservasUsuario(@Param("idUsuario") int idUsuario);

    @Query(value = "SELECT * FROM catpuccino.reserva WHERE fecha = CURRENT_DATE AND estado_reserva = 2", nativeQuery = true)
    List<Reserva> reservasDelDiaPagadas();
}





