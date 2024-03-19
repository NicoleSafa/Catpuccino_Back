package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.ProductoMapper;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.models.Producto;
import com.example.catpuccino_back.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;


    @GetMapping(value="/producto")
    public List<ProductoDTO> listarProducto(){
        return productoService.listarProducto();}

    @GetMapping(value="/producto/{id}")
    public ResponseEntity<ProductoDTO>getById(@PathVariable("id")int id){
        ProductoDTO producto = productoMapper.toDTO(productoService.obtenerUno(id).get());
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping(value="/producto")
    public ProductoDTO crear (@RequestBody ProductoDTO productoDTO){return productoService.crearProducto(productoDTO);}

    @PutMapping(value="/producto/{id}")
    public Producto modificarProducto(@RequestBody ProductoDTO productoDTO) {return productoService.editarProducto(productoDTO);}

    @DeleteMapping(value="/producto/{id}")
    public String eliminar(@PathVariable("id") Integer id){return this.productoService.eliminarProducto(id);}



}