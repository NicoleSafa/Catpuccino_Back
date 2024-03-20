package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.SolicitudMapper;
import com.example.catpuccino_back.dto.SolicitudDTO;
import com.example.catpuccino_back.repository.SolicitudRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class SolicitudService {
    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    //LISTAR
    public List<SolicitudDTO> listarSolicitudes(){
        return solicitudMapper.toDTO(solicitudRepository.findAll());}
}
