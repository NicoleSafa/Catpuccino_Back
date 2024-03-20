package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.GatoMapper;
import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.repository.GatoRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class GatoService {
    @Autowired
    private GatoRepository gatoRepository;

    @Autowired
    private GatoMapper gatoMapper;

    //LISTAR
    public List<GatoDTO> listarGato(){return gatoMapper.toDTO(gatoRepository.findAll());}

    public Gato getByIdGato (Integer id){
        return gatoRepository.findById(id).orElse(null);
    }
}
