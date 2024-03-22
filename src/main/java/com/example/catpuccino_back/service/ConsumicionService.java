package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.ConsumicionMapper;
import com.example.catpuccino_back.dto.ConsumicionDTO;
import com.example.catpuccino_back.models.Consumicion;
import com.example.catpuccino_back.models.Producto;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.repository.ConsumicionRepository;
import com.example.catpuccino_back.repository.ProductoRepository;
import com.example.catpuccino_back.repository.ReservaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Getter
@Service
public class ConsumicionService {
    @Autowired
    private ConsumicionRepository consumicionRepository;

    @Autowired
    private ConsumicionMapper consumicionMapper;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ProductoRepository productoRepository;


    //LISTAR
    public List<ConsumicionDTO> listarconsumiciones() {
        return consumicionMapper.toDTO(consumicionRepository.findAll());
    }

    //SOLO UNO
    public Optional<Consumicion> obtenerUno(int id) {
        return consumicionRepository.findById(id);
    }

    //CREAR
    public ConsumicionDTO crearConsumicion(ConsumicionDTO consumicionDTO) {
        return consumicionMapper.toDTO(consumicionRepository.save(consumicionMapper.toEntity(consumicionDTO)));

    }

    //EDITAR
    public Consumicion editarConsumicion(ConsumicionDTO consumicionDTO) {
        Consumicion consumicion = consumicionRepository.findById(consumicionDTO.getId()).orElse(null);
        if (consumicion == null) {
            return null;
        }else{
            Reserva reserva = reservaRepository.findById(consumicionDTO.getReservaDTO().getId()).orElse(null);
            Producto producto = productoRepository.findById(consumicionDTO.getProductoDTO().getId()).orElse(null);
            consumicion.setCantidad(consumicionDTO.getCantidad());
            consumicion.setId_reserva(reserva);
            consumicion.setId_producto(producto);

            Consumicion consumicionEditada = consumicionRepository.save(consumicion);
            return consumicionEditada;
        }
    }

    //BORRAR
    public String eliminarConsumicion(Integer id){
        if(id==null){
            return("No se ha encontrado la consumicion");
        }else{
            consumicionRepository.deleteById(id);
            return("Consumicion eliminada");
        }
    }

}