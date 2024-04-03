package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.ReservaMapper;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaMapper reservaMapper;

    @GetMapping(value="/reserva")
    public List<ReservaDTO> listarReserva(){
        return reservaService.listarReservas();
    }

    @GetMapping(value = "/reserva/ultima/{id}")
    public Integer obtenerUltimaReservaUsuario(@PathVariable("id") int id) {
        return reservaService.ultimareserva(id);

    }

}
