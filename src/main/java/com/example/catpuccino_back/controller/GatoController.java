package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.GatoMapper;
import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.models.enums.Raza;
import com.example.catpuccino_back.repository.GatoRepository;
import com.example.catpuccino_back.service.GatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/gato")
public class GatoController {

    @Autowired
    private GatoService gatoService;

    private GatoMapper gatoMapper;


    @GetMapping(value="/getAll")
    public List<GatoDTO>listarGato(){
        return gatoService.listarGato();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Gato> getById(@PathVariable("id") int id){
        Gato gato = gatoService.oneByOne(id).get();
        return new ResponseEntity<>(gato, HttpStatus.OK);
    }

    @PostMapping(value = "/crear")
    public GatoDTO crearGato(@RequestBody GatoDTO gatoDTO){
        return gatoService.crearGato(gatoDTO);
    }

    @PutMapping(value = "/editar")
    public Gato editarGato(@RequestBody GatoDTO gatoDTO){
        return gatoService.editarGato(gatoDTO);
    }

    @DeleteMapping(value = "/eliminar")
    public String eliminarGato(@RequestBody GatoDTO gatoDTO){
        return gatoService.eliminarGato(gatoDTO);
    }


    //------------FILTROS---------------
    @GetMapping(value = "/gatosDisponibles")
    public List<GatoDTO> getGatosDisponibles(){
        return gatoService.getGatosDisponibles();
    }

    @GetMapping(value = "/getRazas")
    public List<Raza> getAllRazas(){
        return Arrays.asList(Raza.values());
    }


}
