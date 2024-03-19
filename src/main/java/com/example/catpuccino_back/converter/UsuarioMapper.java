package com.example.catpuccino_back.converter;

import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario entity);

    Usuario toEntity(UsuarioDTO dto);

    List<UsuarioDTO> toDTO(List<Usuario>listEntity);

    List<Usuario>toEntity(List<UsuarioDTO> listDTOs);

}
