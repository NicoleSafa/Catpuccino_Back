package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

    @Query(value = "select s.* from catpuccino.solicitud s where s.estado  = %:enumEstadoSolicitud%", nativeQuery = true)
    List<Solicitud> getSolicitudByEstado(int enumEstadoSolicitud);
}
