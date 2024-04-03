package com.example.catpuccino_back.repository;

import com.example.catpuccino_back.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    //Filtro por tipo de producto
    @Query(value= "select * from catpuccino.producto where tipo=%:tipoProducto", nativeQuery = true)
    List<Producto> filtroTipoProducto (Integer tipoProducto);
}