package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.AdopcionMapper;
import com.example.catpuccino_back.converter.GatoMapper;
import com.example.catpuccino_back.converter.SolicitudMapper;
import com.example.catpuccino_back.converter.UsuarioMapper;
import com.example.catpuccino_back.dto.AdopcionDTO;
import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.dto.SolicitudDTO;
import com.example.catpuccino_back.dto.UsuarioDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.models.Solicitud;
import com.example.catpuccino_back.models.Usuario;
import com.example.catpuccino_back.models.enums.EstadoSolicitud;
import com.example.catpuccino_back.repository.AdopcionRepository;
import com.example.catpuccino_back.repository.GatoRepository;
import com.example.catpuccino_back.repository.SolicitudRepository;
import com.example.catpuccino_back.repository.UsuarioRepository;
import lombok.Getter;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private UsuarioMapper usuarioMapper;
    @Autowired
    private GatoRepository gatoRepository;
    @Autowired
    private GatoMapper gatoMapper;
    @Autowired
    private AdopcionRepository adopcionRepository;
    @Autowired
    private AdopcionMapper adopcionMapper;


    public List<SolicitudDTO> listarSolicitudes() {
        return solicitudMapper.toDTO(solicitudRepository.findAll());
    }

    public SolicitudDTO newSolicitud(SolicitudDTO solicitudDTO) {
        return solicitudMapper.toDTO(solicitudRepository.save(solicitudMapper.toEntity(solicitudDTO)));
    }

    public Optional<Solicitud> oneByOne(int id){
        return solicitudRepository.findById(id);
    }

    //ACTUALIZAR ESTADOS SOLICITUD PENDIENTES A RECHAZADOS
    public void actualizarEstadosSolicitud(SolicitudDTO solicitudDTO, List<SolicitudDTO> listSolicitudDTOs){
        for (SolicitudDTO solicitudAEditar : listSolicitudDTOs) {
            if (solicitudAEditar.getGatoDTO().getId().equals(solicitudDTO.getGatoDTO().getId())
                    && solicitudAEditar.getEstadoSolicitud().equals(EstadoSolicitud.PENDIENTE)) {
                solicitudAEditar.setEstadoSolicitud(EstadoSolicitud.RECHAZADO);
                solicitudRepository.save(solicitudMapper.toEntity(solicitudAEditar));
            }
        }
    }

    //ACTUALIZAR DISPONIBILIDAD DE GATOS ADOPTADOS
    public void actualizarDisponibilidadGato(GatoDTO gatoDTO){
        Gato gato = gatoRepository.findById(gatoDTO.getId()).orElse(null);

        gato.setDisponible(Boolean.FALSE);
    }

    //ACEPTAR SOLICITUD Y RECHAZAR LAS SOLICITUDES PENDIENTES CON ESE MISMO ID Y GUARDAR EN TABLA ADOPCIÓN
    public String aceptarSolicitud(int id) {
        //primero busco la solicitud por su id
        SolicitudDTO solicitudAAceptar = solicitudMapper.toDTO(solicitudRepository.findById(id).orElse(null));

        //edito dicha solicitud y la acepto
        if (solicitudAAceptar == null) {
            return null;
        } else {
            UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuarioRepository.findById(solicitudAAceptar.getUsuarioDTO().getId()).orElse(null));
            solicitudAAceptar.setUsuarioDTO(usuarioDTO);
            GatoDTO gatoDTO = gatoMapper.toDTO(gatoRepository.findById(solicitudAAceptar.getGatoDTO().getId()).orElse(null));
            solicitudAAceptar.setGatoDTO(gatoDTO);
            solicitudAAceptar.setTitulo(solicitudAAceptar.getTitulo());
            solicitudAAceptar.setMensaje(solicitudAAceptar.getMensaje());
            solicitudAAceptar.setEstadoSolicitud(EstadoSolicitud.ACEPTADO);
            solicitudRepository.save(solicitudMapper.toEntity(solicitudAAceptar));

            actualizarDisponibilidadGato(gatoDTO);

            if (EstadoSolicitud.ACEPTADO.equals(solicitudAAceptar.getEstadoSolicitud())){
                AdopcionDTO adopcionDTO = new AdopcionDTO();
                adopcionDTO.setGatoDTO(gatoDTO);
                adopcionDTO.setUsuarioDTO(usuarioDTO);
                Date fechaActual = new Date();
                adopcionDTO.setFecha(fechaActual);

                adopcionRepository.save(adopcionMapper.toEntity(adopcionDTO));
            }
        }

        listarSolicitudes();
        actualizarEstadosSolicitud(solicitudAAceptar, listarSolicitudes());
        return "Solicitud aceptada correctamente";
    }

    public String rechazarSolicitud(int id) {
        //primero busco la solicitud por su id
        SolicitudDTO solicitudARechazar = solicitudMapper.toDTO(solicitudRepository.findById(id).orElse(null));

        //edito dicha solicitud y la acepto
        if (solicitudARechazar == null) {
            return null;
        } else {
            UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuarioRepository.findById(solicitudARechazar.getUsuarioDTO().getId()).orElse(null));
            solicitudARechazar.setUsuarioDTO(usuarioDTO);
            GatoDTO gatoDTO = gatoMapper.toDTO(gatoRepository.findById(solicitudARechazar.getGatoDTO().getId()).orElse(null));
            solicitudARechazar.setGatoDTO(gatoDTO);
            solicitudARechazar.setTitulo(solicitudARechazar.getTitulo());
            solicitudARechazar.setMensaje(solicitudARechazar.getMensaje());
            solicitudARechazar.setEstadoSolicitud(EstadoSolicitud.RECHAZADO);
            solicitudRepository.save(solicitudMapper.toEntity(solicitudARechazar));
        }
        return "Solicitud rechazada correctamente";
    }

    public String cancelarSolicitud(int id){
        SolicitudDTO solicitudACancelar = solicitudMapper.toDTO(solicitudRepository.findById(id).orElse(null));

        if (solicitudACancelar == null) {
            return null;
        } else {
            UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuarioRepository.findById(solicitudACancelar.getUsuarioDTO().getId()).orElse(null));
            solicitudACancelar.setUsuarioDTO(usuarioDTO);
            GatoDTO gatoDTO = gatoMapper.toDTO(gatoRepository.findById(solicitudACancelar.getGatoDTO().getId()).orElse(null));
            solicitudACancelar.setGatoDTO(gatoDTO);
            solicitudACancelar.setTitulo(solicitudACancelar.getTitulo());
            solicitudACancelar.setMensaje(solicitudACancelar.getMensaje());
            solicitudACancelar.setEstadoSolicitud(EstadoSolicitud.CANCELADA);
            solicitudRepository.save(solicitudMapper.toEntity(solicitudACancelar));
        }
        return "Solicitud cancelada correctamente";
    }

    //------------FILTROS---------------
    public List<SolicitudDTO> getSolicitudByEstado(int enumEstadoSolicitud){
        return solicitudMapper.toDTO(solicitudRepository.getSolicitudByEstado(enumEstadoSolicitud));
    }

    public Integer getNumSolicitudByGato(int idGato){
        return solicitudRepository.getNumSolicitudByGato(idGato);
    }

    public List<SolicitudDTO> getSolicitudByUsuario(int idUsuario){
        return solicitudMapper.toDTO(solicitudRepository.getSolicitudByUsuario(idUsuario));
    }


}
