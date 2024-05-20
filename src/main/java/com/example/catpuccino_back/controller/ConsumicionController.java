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
@RequestMapping("/consumiciones")
public class ConsumicionController {
    @Autowired
    private ConsumicionService consumicionService;
    @Autowired
    private ConsumicionMapper consumicionMapper;


    @GetMapping(value = "")
    public List<ConsumicionDTO> listarConsumisiones() {
        return consumicionService.listarconsumiciones();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsumicionDTO> getById(@PathVariable("id") int id) {
        ConsumicionDTO producto = consumicionMapper.toDTO(consumicionService.obtenerUno(id).get());
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ConsumicionDTO crear(@RequestBody ConsumicionDTO consumicionDTO) {
        return consumicionService.crearConsumicion(consumicionDTO);
    }

    @PutMapping(value = "/{id}")
    public Consumicion modificarConsumicion(@RequestBody ConsumicionDTO consumicionDTO) {
        return consumicionService.editarConsumicion(consumicionDTO);
    }

    @DeleteMapping(value = "/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        return this.consumicionService.eliminarConsumicion(id);
    }

    @PostMapping(value = "/carrito/agregar")
    public ResponseEntity<String> agregarProductoAlCarrito(@RequestParam("idProducto") Integer idProducto,
                                                           @RequestParam("cantidad") Integer cantidad,
                                                           @RequestParam("idUsuario") Integer idUsuario) {
        consumicionService.agregarProductoAlCarrito(idProducto, cantidad, idUsuario);
        return new ResponseEntity<>("Producto agregado al carrito", HttpStatus.OK);
    }

    @PostMapping(value = "/pedido")
    public String carrito (){
        consumicionService.listaCarrito();
        return  ("Producto agregado al carrito");
    }

    @GetMapping(value="/verCarro")
    public List mostrarLista(){
        return consumicionService.mostrarLista();
    }

    @GetMapping(value="/consumicionReserva/{id}")
    public List<ConsumicionDTO>consumicionesReserva(@PathVariable("id") int idReserva){
        return consumicionService.obtenerConsumicionesReserva(idReserva);
    }



}
