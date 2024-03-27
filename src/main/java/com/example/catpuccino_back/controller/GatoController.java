package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.GatoMapper;
import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.repository.GatoRepository;
import com.example.catpuccino_back.service.GatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class GatoController {

    @Autowired
    private GatoService gatoService;

    private GatoMapper gatoMapper;


    @GetMapping(value="/gatos")
    public List<GatoDTO>listarGato(){
        return gatoService.listarGato();
    }

    @GetMapping(value = "/gato/{id}")
    public ResponseEntity<Gato> getById(@PathVariable("id") int id){
        Gato gato = gatoService.oneByOne(id).get();
        return new ResponseEntity<>(gato, HttpStatus.OK);
    }

    @PostMapping(value = "/gato/crear")
    public GatoDTO crearGato(@RequestBody GatoDTO gatoDTO){
        return gatoService.crearGato(gatoDTO);
    }

    @PutMapping(value = "/gato/editar")
    public Gato editarGato(@RequestBody GatoDTO gatoDTO){
        return gatoService.editarGato(gatoDTO);
    }

    @DeleteMapping(value = "/gato/eliminar")
    public String eliminarGato(@RequestBody GatoDTO gatoDTO){
        return gatoService.eliminarGato(gatoDTO);
    }
}
