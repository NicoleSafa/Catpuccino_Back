package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.SolicitudMapper;
import com.example.catpuccino_back.dto.SolicitudDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.models.Solicitud;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.models.enums.EstadoSolicitud;
import com.example.catpuccino_back.repository.GatoRepository;
import com.example.catpuccino_back.repository.SolicitudRepository;
import com.example.catpuccino_back.repository.UsuarioRepository;
import lombok.Getter;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private SolicitudMapper solicitudMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GatoRepository gatoRepository;



    public List<SolicitudDTO> listarSolicitudes(){
        return solicitudMapper.toDTO(solicitudRepository.findAll());
    }

    public SolicitudDTO newSolicitud(SolicitudDTO solicitudDTO){
        return solicitudMapper.toDTO(solicitudRepository.save(solicitudMapper.toEntity(solicitudDTO)));
    }

    //EDITAR ESTADO SOLICITUD
    public String editarEstadoSolicitud(SolicitudDTO solicitudDTO, List<SolicitudDTO> listSolicitudesDTO){
        for (SolicitudDTO solicitudAEditar: listSolicitudesDTO){
            if (solicitudAEditar.getGatoDTO().getId().equals(solicitudDTO.getGatoDTO().getId())
                && solicitudAEditar.getEstadoSolicitud().equals(EstadoSolicitud.PENDIENTE)){
                solicitudAEditar.setEstadoSolicitud(EstadoSolicitud.RECHAZADO);
                solicitudRepository.save(solicitudMapper.toEntity(solicitudAEditar));
            }
        }
        return "Estado solicitud actualizado";
    }

    //ACEPTAR SOLICITUD Y RECHAZAR LAS SOLICITUDES PENDIENTES CON ESE MISMO ID
//    public String aceptarSolicitud(SolicitudDTO solicitudDTO, List<SolicitudDTO> listSolicitudesDTO){
//        //primero busco la solicitud por su id
//        Solicitud solicitudAAceptar = solicitudRepository.findById(solicitudDTO.getId()).orElse(null);
//
//        //edito dicha solicitud y la acepto
//        if (solicitudAAceptar == null){
//            return null;
//        } else {
//            Usuario usuario = usuarioRepository.findById(solicitudDTO.getUsuarioDTO().getId()).orElse(null);
//            solicitudAAceptar.setIdUsuario(usuario);
//            Gato gato = gatoRepository.findById(solicitudDTO.getGatoDTO().getId()).orElse(null);
//            solicitudAAceptar.setIdGato(gato);
//            solicitudAAceptar.setTitulo(solicitudDTO.getTitulo());
//            solicitudAAceptar.setMensaje(solicitudDTO.getMensaje());
//            solicitudAAceptar.setEstadoSolicitud(EstadoSolicitud.ACEPTADO);
//
//            //necesito buscar las demas solicitudes que tengan el mismo id y con el estado de PENDIENTE, si hay, rechazarlas
//        }
//
//    }


}
