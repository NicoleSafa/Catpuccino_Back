package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Consumicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumicionRepository extends JpaRepository<Consumicion, Integer>{
    @Query (value = "select * from catpuccino.consumicion c " +
            "where c.id_reserva = :idReserva and c.id_producto = :idProducto", nativeQuery = true)
    Integer findByReservaIdAndProductoId (Integer idReserva, Integer idProducto);

    @Query(value="select * from catpuccino.consumicion c where c.id_reserva=:idReserva", nativeQuery = true)
    List<Consumicion> consumicionesReserva(@Param("idReserva") int idReserva);

    @Query(value="select sum(c.cantidad * p.precio) from catpuccino.consumicion c join catpuccino.producto p on p.id = c.id_producto where c.id_reserva = :idReserva", nativeQuery = true)
    Double calcularTotalPorReserva(@Param("idReserva") int idReserva);
}
