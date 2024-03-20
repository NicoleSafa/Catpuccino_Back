package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.AdopcionMapper;
import com.example.catpuccino_back.dto.AdopcionDTO;
import com.example.catpuccino_back.service.AdopcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class AdopcionController {

    @Autowired
    private AdopcionService adopcionService;

    @Autowired
    private AdopcionMapper adopcionMapper;

    @GetMapping(value="/adopcion")
    public List<AdopcionDTO>listarAdopcion(){return adopcionService.listarAdopciones();}
}
