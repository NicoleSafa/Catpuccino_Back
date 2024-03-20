package com.example.catpuccino_back.service;


import com.example.catpuccino_back.converter.ReservaMapper;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Reserva;
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

    //Metodo para ReservaService
    public Reserva getByIdReserva(Integer id){
        return reservaRepository.findById(id).orElse(null);
    }


    //LISTAR
    public List<ReservaDTO> listarReservas() {return reservaMapper.toDTO(reservaRepository.findAll());}
}
