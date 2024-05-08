package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Gato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatoRepository  extends JpaRepository<Gato, Integer> {

    @Query(value = "select g.* from catpuccino.gato g where g.disponible = true order by id asc", nativeQuery = true)
    List<Gato> getGatosDisponibles();

//    @Query(value = "select count(g.id) as conteo, g.* from catpuccino.gato g join catpuccino.solicitud s on s.id_gato = g.id where g.disponible = true group by g.id order by g.id asc", nativeQuery = true)
//    List<Object> getGatosDisponiblesYNumSolicitudes();
}

