package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Consumicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumicionRepository extends JpaRepository<Consumicion, Integer>{
    @Query (value = "select * from catpuccino.consumicion c " +
            "where c.id_reserva = :idReserva and c.id_producto = :idProducto", nativeQuery = true)
    Integer findByReservaIdAndProductoId (Integer idReserva, Integer idProducto);

}
