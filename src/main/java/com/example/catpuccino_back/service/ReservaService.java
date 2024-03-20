package com.example.catpuccino_back.service;


import com.example.catpuccino_back.converter.ReservaMapper;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Getter;
import java.util.List;


@Getter
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    //LISTAR
    public List<ReservaDTO> listarReservas() {return reservaMapper.toDTO(reservaRepository.findAll());}
}
