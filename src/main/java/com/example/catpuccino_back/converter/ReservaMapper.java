package com.example.catpuccino_back.converter;

import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Reserva;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ReservaMapper {
    ReservaDTO toDTO(Reserva entity);

    Reserva toEntity(ReservaDTO dto);

    List<ReservaDTO> toDTO(List<Reserva>listEntity);

    List<Reserva>toEntity(List<ReservaDTO> listDTOs);
}
