package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.AdopcionMapper;
import com.example.catpuccino_back.dto.AdopcionDTO;
import com.example.catpuccino_back.repository.AdopcionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class AdopcionService {
    @Autowired
    private AdopcionRepository adopcionRepository;

    @Autowired
    private AdopcionMapper adopcionMapper;

    //LISTAR
    public List<AdopcionDTO> listarAdopciones() {return adopcionMapper.toDTO(adopcionRepository.findAll());}

    //------------FILTROS---------------
    public  Integer getNumAdopciones(){
        return adopcionRepository.getNumAdopciones();
    }
}
