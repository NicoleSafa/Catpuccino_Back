package com.example.catpuccino_back.repository;


import com.example.catpuccino_back.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    //Filtro de comprobación de reserva disponible con la fecha y la hora
    @Query(value = "SELECT COUNT(*) FROM catpuccino.reserva r WHERE r.fecha = :fecha AND r.hora BETWEEN :hora AND (CAST(:hora AS TIME) + INTERVAL '59 minutes') AND r.reserva_activa = true", nativeQuery = true)
    int disponibilidadHoras(Time hora, Date fecha);

    //TE PONE LA ÚLTIMA RESERVA LIGADA AL USUARIO
    @Query(value = "    SELECT r.id" +
            "        FROM catpuccino.reserva r" +
            "        JOIN catpuccino.usuario u ON u.id = r.id_usuario" +
            "        WHERE r.reserva_activa = true AND u.id = :id;", nativeQuery = true)
    Integer ultimareservausuario(int id);

}



