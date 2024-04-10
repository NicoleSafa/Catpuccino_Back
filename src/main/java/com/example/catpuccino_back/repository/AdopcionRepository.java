package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Adopcion;
import com.example.catpuccino_back.models.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {

    @Query(value = "select count(a.id)  from catpuccino.adopcion a", nativeQuery = true)
    Integer getNumAdopciones();

}
