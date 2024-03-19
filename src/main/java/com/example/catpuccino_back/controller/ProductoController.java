package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping(value="/producto")
    public List<ProductoDTO> listarProducto(){
        return productoService.listarProducto();}

}