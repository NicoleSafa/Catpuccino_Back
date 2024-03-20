package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.GatoMapper;
import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.repository.GatoRepository;
import com.example.catpuccino_back.service.GatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class GatoController {

    @Autowired
    private GatoService gatoService;

    private GatoMapper gatoMapper;


    @GetMapping(value="/gato")
    public List<GatoDTO>listarGato(){
        return gatoService.listarGato();
    }
}
