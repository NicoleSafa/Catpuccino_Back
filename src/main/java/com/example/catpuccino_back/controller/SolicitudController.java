package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.SolicitudMapper;
import com.example.catpuccino_back.dto.SolicitudDTO;
import com.example.catpuccino_back.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class SolicitudController {
    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @GetMapping(value="/solicitud")
    public List<SolicitudDTO> listarSolicitud(){return solicitudService.listarSolicitudes();}
}
