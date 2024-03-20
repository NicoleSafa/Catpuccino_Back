package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping(value="/usuario")
    public List<UsuarioDTO> listarUsuario(){
        return usuarioService.listarUsuario();
    }
}
