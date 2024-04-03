package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Consumicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumicionRepository extends JpaRepository<Consumicion, Integer>{


}
