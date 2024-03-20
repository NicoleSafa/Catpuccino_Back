package com.example.catpuccino_back.controller;

import com.example.catpuccino_back.converter.ConsumicionMapper;
import com.example.catpuccino_back.dto.ConsumicionDTO;
import com.example.catpuccino_back.service.ConsumicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
public class ConsumicionController {
    @Autowired
    private ConsumicionService consumicionService;

    @Autowired
    private ConsumicionMapper consumicionMapper;


    @GetMapping(value="/consumiciones")
    public List<ConsumicionDTO> listarConsumisiones(){
        return consumicionService.listarconsumiciones();
    }

}
