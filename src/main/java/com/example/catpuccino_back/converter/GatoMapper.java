package com.example.catpuccino_back.converter;

import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.models.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface GatoMapper {

    GatoDTO toDTO(Gato entity);

    Gato toEntity(GatoDTO dto);

    List<GatoDTO> toDTO(List<Gato>listEntity);

    List<Gato>toEntity(List<GatoDTO> listDTOs);
}

