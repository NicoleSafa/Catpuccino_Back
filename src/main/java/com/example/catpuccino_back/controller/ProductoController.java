package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.ProductoMapper;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}