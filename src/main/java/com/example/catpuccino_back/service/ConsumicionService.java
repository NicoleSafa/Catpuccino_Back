package com.example.catpuccino_back.service;

import com.example.catpuccino_back.converter.ConsumicionMapper;
import com.example.catpuccino_back.converter.ProductoMapper;
import com.example.catpuccino_back.converter.ReservaMapper;
import com.example.catpuccino_back.dto.ConsumicionDTO;
import com.example.catpuccino_back.dto.ProductoDTO;
import com.example.catpuccino_back.dto.ReservaDTO;
import com.example.catpuccino_back.models.Consumicion;
import com.example.catpuccino_back.models.Producto;
import com.example.catpuccino_back.models.Reserva;
import com.example.catpuccino_back.models.enums.EstadoReserva;
import com.example.catpuccino_back.repository.ConsumicionRepository;
import com.example.catpuccino_back.repository.ProductoRepository;
import com.example.catpuccino_back.repository.ReservaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
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
    private ReservaService reservaService;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;


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
        } else {
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
    public String eliminarConsumicion(Integer id) {
        if (id == null) {
            return ("No se ha encontrado la consumicion");
        } else {
            consumicionRepository.deleteById(id);
            return ("Consumicion eliminada");
        }
    }

    //CARRITO
    private List<ConsumicionDTO> carrito = new ArrayList<>();

    public void agregarProductoAlCarrito(Integer idProducto, Integer cantidad, Integer idUsuario) {
        Integer idReserva = reservaService.ultimareserva(idUsuario);
        Producto producto = productoService.getByIdProducto(idProducto);
        ProductoDTO productoDTO = productoMapper.toDTO(producto);
        Reserva reserva = reservaService.getByIdReserva(idReserva);
        ReservaDTO reservaDTO = reservaMapper.toDTO(reserva);

        if (productoDTO != null && reservaDTO != null) {
            boolean productoEncontrado = false;

            // Recorre la lista carrito para buscar si el producto ya está agregado
            for (ConsumicionDTO consumicion : carrito) {
                // Comprueba si el producto en el carrito es igual al producto a agregar
                if (consumicion.getProductoDTO().getId().equals(productoDTO.getId()) && consumicion.getReservaDTO().getId().equals(reservaDTO.getId())) {
                    // Si son iguales, aumenta la cantidad en lugar de agregar uno nuevo
                    consumicion.setCantidad(consumicion.getCantidad() + cantidad);
                    productoEncontrado = true;
                    break; // Termina el bucle ya que ya se encontró el producto
                }
            }

            // Si el producto no se encontró en el carrito, agrégalo normalmente
            if (!productoEncontrado) {
                ConsumicionDTO consumicion = ConsumicionDTO.builder()
                        .productoDTO(productoDTO)
                        .cantidad(cantidad)
                        .reservaDTO(reservaDTO)
                        .build();

                carrito.add(consumicion);
            }
        }
    }
    //ME ENSEÑA LA LISTA QUE TENGO PARA METER Y ESO
    public List mostrarLista(){
        return carrito;
    }

    //FORMALIZAR PEDIDO
    public void listaCarrito() {
        for (ConsumicionDTO consumicion : carrito) {
            Integer reservaId = consumicion.getReservaDTO().getId();
            Integer productoId = consumicion.getProductoDTO().getId();
            Integer cantidad = consumicion.getCantidad();

            Integer existingConsumptionId = consumicionRepository.findByReservaIdAndProductoId(reservaId, productoId);

            if (existingConsumptionId != null) {
                Consumicion existingConsumption = consumicionRepository.getOne(existingConsumptionId);
                existingConsumption.setCantidad(existingConsumption.getCantidad() + cantidad);
                consumicionRepository.save(existingConsumption);
            } else {
                Consumicion consumicionEntidad = consumicionMapper.toEntity(consumicion);
                consumicionRepository.save(consumicionEntidad);
            }
        }
        carrito.clear();
    }

    //las consumiciones por la reserva
    public List<ConsumicionDTO> obtenerConsumicionesReserva(@Param("idReserva") int idReserva){
        List<Consumicion> consumicion = consumicionRepository.consumicionesReserva(idReserva);
        List<ConsumicionDTO> consumicionesDTO = consumicionMapper.toDTO(consumicion);
        return consumicionesDTO;
    }

    //Las consumiciones por reserva pero que tambien setea los campos de reserva para ponerla como pagada con el precio
    public List<ConsumicionDTO> pagoCamarero(int idReserva){
        List<Consumicion> consumicion = consumicionRepository.consumicionesReserva(idReserva);
        List<ConsumicionDTO> consumicionesDTO = consumicionMapper.toDTO(consumicion);

        Double total = consumicionRepository.calcularTotalPorReserva(idReserva);

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> null);
        reserva.setTotal(total);
        reserva.setReserva_activa(false);
        reserva.setPagado(Boolean.TRUE);
        reserva.setEstadoReserva(EstadoReserva.PAGADO);
        reservaRepository.save(reserva);

        return consumicionesDTO;
    }

    public boolean existenConsumicionesParaReserva(int idReserva) {
        return consumicionRepository.existenConsumicionesReserva(idReserva);
    }





}