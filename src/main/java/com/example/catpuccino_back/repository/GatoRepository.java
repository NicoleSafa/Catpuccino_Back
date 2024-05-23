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

}

