package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.ProductoMapper;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.models.Producto;
import com.example.catpuccino_back.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Getter;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    //Metodo para ReservaService
    public Producto getByIdProducto(Integer id){
        return productoRepository.findById(id).orElse(null);
    }

    //LISTAR
    public List<ProductoDTO> listarProducto(){return productoMapper.toDTO(productoRepository.findAll());}

    //SOLO UNO
    public Optional<Producto> obtenerUno(int id){return productoRepository.findById(id);}

    //CREAR
    public ProductoDTO crearProducto(ProductoDTO productoDTO){
        Producto productoGuardar = productoMapper.toEntity(productoDTO);
        Producto productoGuardado = productoRepository.save(productoGuardar);
        ProductoDTO productoGuardadoDTO = productoMapper.toDTO(productoGuardado);
        return productoGuardadoDTO;
    }

    //EDITAR
    public Producto editarProducto (ProductoDTO productoDTO){
        Producto producto = productoRepository.findById(productoDTO.getId()).orElse(null);
        if (producto == null){
            return null;
        }else{
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setTipo(productoDTO.getTipo());
        }

        Producto productoModificado = productoRepository.save(producto);
        return productoModificado;
    }

    //BORRAR
    public String eliminarProducto(Integer id){
        if(id==null){
            return ("No se ha encontrado el producto");
        }else{
            productoRepository.deleteById(id);
            return ("Producto eliminado");
        }
    }
}