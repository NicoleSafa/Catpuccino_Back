package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.ProductoMapper;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> listarProducto(){
        return productoMapper.toDTO(productoRepository.findAll());}
}