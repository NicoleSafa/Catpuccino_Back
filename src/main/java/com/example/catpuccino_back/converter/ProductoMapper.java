package com.example.catpuccino_back.converter;

import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.models.Producto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface ProductoMapper {
    ProductoDTO toDTO(Producto entity);

    Producto toEntity(ProductoDTO dto);

    List<ProductoDTO> toDTO(List<Producto>listEntity);

    List<Producto>toEntity(List<ProductoDTO> listDTOs);
}