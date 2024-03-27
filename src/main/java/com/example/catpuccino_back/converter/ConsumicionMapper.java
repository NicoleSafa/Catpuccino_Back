package com.example.catpuccino_back.converter;


import com.example.catpuccino_back.dto.ConsumicionDTO;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Consumicion;
import com.example.catpuccino_back.models.Producto;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.service.ProductoService;
import com.example.catpuccino_back.service.ReservaService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper (componentModel = "spring")
public abstract class ConsumicionMapper {

    @Autowired
    protected ReservaService reservaService;
    @Autowired
    protected ProductoService productoService;

    ReservaMapper reservaMapper = Mappers.getMapper(ReservaMapper.class);
    ProductoMapper productoMapper = Mappers.getMapper(ProductoMapper.class);


    @Mapping(source = "id_reserva", target = "reservaDTO",  qualifiedByName = "conversorReservaDTO")
    @Mapping(source = "id_producto", target = "productoDTO",  qualifiedByName = "conversorProductoDTO")
    public abstract ConsumicionDTO toDTO(Consumicion entity);
    @Mapping(source = "reservaDTO", target = "id_reserva",  qualifiedByName = "conversorReservaEntity")
    @Mapping(source = "productoDTO", target = "id_producto",  qualifiedByName = "conversorProductoEntity")
    public abstract Consumicion toEntity(ConsumicionDTO dto);

    public abstract List<ConsumicionDTO> toDTO(List<Consumicion>listEntity);

    public abstract List<Consumicion>toEntity(List<ConsumicionDTO> listDTOs);

    //CONVERSORES PARA DTO
    @Named(value = "conversorReservaDTO")
    ReservaDTO conversorReserva(Reserva entity){
        return reservaMapper.toDTO(entity);
    }

    @Named(value = "conversorProductoDTO")
    ProductoDTO conversorProducto(Producto entity){
        return productoMapper.toDTO(entity);
    }

    //CONVERSORES PARA ENTITY
    @Named(value = "conversorReservaEntity")
    Reserva conversorReserva(ReservaDTO dto){
        Reserva reserva = reservaService.getByIdReserva(dto.getId());
        return reserva;
    }
    @Named(value = "conversorProductoEntity")
    Producto conversorProducto(ProductoDTO dto){
        Producto producto = productoService.getByIdProducto(dto.getId());
        return producto;
    }





}
