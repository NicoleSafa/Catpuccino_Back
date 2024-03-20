package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.ConsumicionMapper;
import com.example.catpuccino_back.dto.ConsumicionDTO;
import com.example.catpuccino_back.repository.ConsumicionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Getter
@Service
public class ConsumicionService {
    @Autowired
    private ConsumicionRepository consumicionRepository;

    @Autowired
    private ConsumicionMapper consumicionMapper;


    //LISTAR
    public List<ConsumicionDTO> listarconsumiciones() {return consumicionMapper.toDTO(consumicionRepository.findAll());}

}
