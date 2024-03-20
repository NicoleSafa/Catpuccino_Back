package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Gato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatoRepository  extends JpaRepository<Gato, Integer> {
}
