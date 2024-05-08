package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.GatoMapper;
import com.example.catpuccino_back.dto.GatoDTO;
import com.example.catpuccino_back.models.Gato;
import com.example.catpuccino_back.models.enums.Raza;
import com.example.catpuccino_back.models.enums.Sexo;
import com.example.catpuccino_back.models.enums.Tamanyo;
import com.example.catpuccino_back.repository.GatoRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class GatoService {
    @Autowired
    private GatoRepository gatoRepository;

    @Autowired
    private GatoMapper gatoMapper;

    //LISTAR
    public List<GatoDTO> listarGato(){return gatoMapper.toDTO(gatoRepository.findAll());}

    public Gato getByIdGato (Integer id){
        return gatoRepository.findById(id).orElse(null);
    }

    public Optional<Gato> oneByOne(int id){
        return gatoRepository.findById(id);
    }

    public GatoDTO crearGato(GatoDTO gatoDTO){
        return gatoMapper.toDTO(gatoRepository.save(gatoMapper.toEntity(gatoDTO)));
    }

    public Gato editarGato(GatoDTO gatoDTO){
        Gato gato = gatoRepository.findById(gatoDTO.getId()).orElse(null);

        if (gato == null){
            return null;
        } else {
            gato.setNombre(gatoDTO.getNombre());
            gato.setImagen(gatoDTO.getImagen());
            gato.setRaza(gatoDTO.getRaza());
            gato.setTamanyo(gatoDTO.getTamanyo());
            gato.setSexo(gatoDTO.getSexo());
            gato.setDisponible(gatoDTO.getDisponible());
            gato.setVacunacionCompleta(gatoDTO.getVacunacionCompleta());
            gato.setChip(gatoDTO.getChip());

            Gato gatoEditado= gatoRepository.save(gato);
            return gatoEditado;
        }
    }

    public String eliminarGato(GatoDTO gatoDTO){
        Gato gatoEliminar = gatoRepository.findById(gatoDTO.getId()).orElse(null);

        if (gatoEliminar != null){
            gatoRepository.delete(gatoEliminar);
            return "Michi eliminado correctamente";
        }else{
            return "No se ha podido eliminar el michi";
        }
    }

    //------------FILTROS---------------
    public  List<GatoDTO> getGatosDisponibles(){
        return gatoMapper.toDTO(gatoRepository.getGatosDisponibles());
    }

//    public  List<GatoDTO> getGatosDisponiblesYNumSolicitudes(){
//        List<Object> resultados = gatoRepository.getGatosDisponiblesYNumSolicitudes();
//        List<GatoDTO> gatosDTO = new ArrayList<>();
//
//        for (Object resultado : resultados) {
//            Object[] datos = (Object[]) resultado;
//
//            Integer gatoId = (Integer) datos[1];
//            String nombre = (String) datos[2];
//            String imagen = (String) datos[3];
//            Raza raza = (Raza) datos[4];
//            Tamanyo tamanyo = (Tamanyo) datos[5];
//            Sexo sexo = (Sexo) datos[6];
//            String descripcionCorta = (String) datos[7];
//            Boolean disponible = (Boolean) datos[8];
//            Boolean vacunacionCompleta = (Boolean) datos[9];
//            String descripcionLarga = (String) datos[10];
//            Boolean chip = (Boolean) datos[11];
//            Boolean necesidadesEspeciales = (Boolean) datos[12];
//            Long conteo = (Long) datos[0];
//
//            GatoDTO gatoDTO = new GatoDTO();
//            gatoDTO.setId(gatoId);
//            gatoDTO.setNombre(nombre);
//            gatoDTO.setImagen(imagen);
//            gatoDTO.setDisponible(disponible);
//            gatoDTO.setConteo(conteo);
//            gatoDTO.setRaza(raza);
//            gatoDTO.setTamanyo(tamanyo);
//            gatoDTO.setSexo(sexo);
//            gatoDTO.setDescripcionCorta(descripcionCorta);
//            gatoDTO.setVacunacionCompleta(vacunacionCompleta);
//            gatoDTO.setDescripcionLarga(descripcionLarga);
//            gatoDTO.setChip(chip);
//            gatoDTO.setNecesidadesEspeciales(necesidadesEspeciales);
//            gatoDTO.setConteo(conteo);
//
//            gatosDTO.add(gatoDTO);
//        }
//
//        return gatosDTO;
//    }

}
