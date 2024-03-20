package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {
}
