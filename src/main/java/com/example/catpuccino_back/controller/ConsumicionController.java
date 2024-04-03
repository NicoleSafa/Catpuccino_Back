package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.ConsumicionMapper;
import com.example.catpuccino_back.dto.ConsumicionDTO;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.models.Consumicion;
import com.example.catpuccino_back.service.ConsumicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class ConsumicionController {
    @Autowired
    private ConsumicionService consumicionService;

    @Autowired
    private ConsumicionMapper consumicionMapper;


    @GetMapping(value = "/consumiciones")
    public List<ConsumicionDTO> listarConsumisiones() {
        return consumicionService.listarconsumiciones();
    }

    @GetMapping(value = "/consumiciones/{id}")
    public ResponseEntity<ConsumicionDTO> getById(@PathVariable("id") int id) {
        ConsumicionDTO producto = consumicionMapper.toDTO(consumicionService.obtenerUno(id).get());
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping(value = "/consumiciones")
    public ConsumicionDTO crear(@RequestBody ConsumicionDTO consumicionDTO) {
        return consumicionService.crearConsumicion(consumicionDTO);
    }

    @PutMapping(value = "/consumiciones/{id}")
    public Consumicion modificarConsumicion(@RequestBody ConsumicionDTO consumicionDTO) {
        return consumicionService.editarConsumicion(consumicionDTO);
    }

    @DeleteMapping(value = "/consumiciones/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        return this.consumicionService.eliminarConsumicion(id);
    }

    @PostMapping(value = "consumiciones/carrito/agregar")
    public ResponseEntity<String> agregarProductoAlCarrito(@RequestParam("idProducto") Integer idProducto,
                                                           @RequestParam("cantidad") Integer cantidad,
                                                           @RequestParam("idReserva") Integer idReserva) {
        consumicionService.agregarProductoAlCarrito(idProducto, cantidad, idReserva);
        return new ResponseEntity<>("Producto agregado al carrito", HttpStatus.OK);
    }


}
