package com.example.catpuccino_back.converter;

import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.service.UsuarioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReservaMapper {

    @Autowired
    protected UsuarioService usuarioService;

    UsuarioMapper usuarioMapper = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "id_usuario", target = "usuarioDTO",  qualifiedByName = "conversorUsuarioDTO")
    public abstract ReservaDTO toDTO(Reserva entity);

    @Mapping(source = "usuarioDTO", target = "id_usuario",  qualifiedByName = "conversorUsuarioEntity")
    public abstract Reserva toEntity(ReservaDTO dto);

    public abstract List<ReservaDTO> toDTO(List<Reserva>listEntity);
    public abstract List<Reserva>toEntity(List<ReservaDTO> listDTOs);


    //CONVERSORES PARA DTO
    @Named(value = "conversorUsuarioDTO")
    UsuarioDTO conversorUsuario(Usuario entity){
        return usuarioMapper.toDTO(entity);
    }

    //CONVERSORES PARA ENTITY
    @Named(value = "conversorUsuarioEntity")
    Usuario conversorUsuario(UsuarioDTO dto){
        Usuario usuario = usuarioService.getByIdUsuario(dto.getId());
        return usuario;
    }





}
