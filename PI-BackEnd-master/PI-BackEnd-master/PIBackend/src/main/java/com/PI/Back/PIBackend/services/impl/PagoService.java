package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Alquiler;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Pago;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.repository.AlquilerRepository;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.repository.PagoRepository;
import com.PI.Back.PIBackend.repository.UsuarioRepository;
import com.PI.Back.PIBackend.services.IPagoService;
import com.PI.Back.PIBackend.utils.JsonPrinter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService implements IPagoService {

    Logger LOGGER = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlquilerService alquilerService;

    @Override
    @Secured("ROLE_USUARIO")
    @Transactional
    public PagoSalidaDto procesarPago(PagoEntradaDto pagoEntradaDto) {

        LOGGER.info("Procesando pago para intrumentoId: {}", pagoEntradaDto.getInstrumentoId());

        //COMENTADO PARA PRUEBA
        //Alquiler alquiler = alquilerRepository.findById(pagoEntradaDto.getAlquilerId())
        //        .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

        Instrumento instrumento = instrumentoRepository.findById(pagoEntradaDto.getInstrumentoId()).orElseThrow(() -> new RuntimeException("Instrumento no encontrado"));

        LOGGER.info("Instrumento encontrado: {}", instrumento.getNombre());

        Usuario usuario = usuarioRepository.findById(pagoEntradaDto.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LOGGER.info("Usuario encontrado: {}", usuario.getEmail());

        //Verifiacion de disponibilidad

        LOGGER.info("Verificando disponibilidad del instrumento para las fechas {} - {}",
                pagoEntradaDto.getFechaInicio(), pagoEntradaDto.getFechaFin());

        if(!alquilerService.instrumentoDisponible(instrumento.getId(), pagoEntradaDto.getFechaInicio(), pagoEntradaDto.getFechaFin())){
            throw new RuntimeException("El instrumento no esta disponible en la fecha seleccionada.");
        }

        //Creando un alquiler en db

        Alquiler alquiler = new Alquiler();

        alquiler.setInstrumento(instrumento);
        alquiler.setUsuario(usuario);
        alquiler.setFechaInicio(pagoEntradaDto.getFechaInicio());
        alquiler.setFechaFin(pagoEntradaDto.getFechaFin());
        alquiler.setMonto(pagoEntradaDto.getMonto());

        Alquiler alquilerGuardado = alquilerRepository.save(alquiler);

        LOGGER.info("Nuevo alquiler creado con ID: {}", alquiler.getId());

        // Verificar si ya existe un pago para este alquiler
        if (pagoRepository.findByAlquiler(alquilerGuardado).isPresent()) {
            throw new RuntimeException("Ya existe un pago asociado a este alquiler.");
        }

        // Verificar si ya existe un pago para este alquiler
        LOGGER.info("Verificando si ya existe un pago para el alquiler con ID: {}", alquilerGuardado.getId());
        Optional<Pago> pagoExistente = pagoRepository.findByAlquiler(alquilerGuardado);
        if (pagoExistente.isPresent()) {
            LOGGER.error("Ya existe un pago asociado a este alquiler con ID: {}", pagoExistente.get().getId());
            throw new DataIntegrityViolationException("Ya existe un pago asociado a este alquiler.");
        }

        Pago pagoIngresado = new Pago();

        pagoIngresado.setMonto(pagoEntradaDto.getMonto());
        pagoIngresado.setMetodoDePago(pagoEntradaDto.getMetodoPago());
        pagoIngresado.setAlquiler(alquilerGuardado);
        pagoIngresado.setUsuario(usuario);
        pagoIngresado.setFechaDePago(LocalDate.now());
        pagoIngresado.setReferenciaTransaccion(generarReferenciaTransaccion());
        pagoIngresado.setEstado("PENDIENTE");
        pagoIngresado.setFechaInicio(pagoEntradaDto.getFechaInicio());
        pagoIngresado.setFechaFin(pagoEntradaDto.getFechaFin());

        LOGGER.info("Guardando pago con referencia transacción: {}, monto: {}, alquilerId: {}",
                pagoIngresado.getReferenciaTransaccion(), pagoIngresado.getMonto(), pagoIngresado.getAlquiler().getId());

        Pago pagoGuardado = pagoRepository.save(pagoIngresado);

        //Actualizar disponibilidad

        instrumento.setDisponible(false);
        instrumentoRepository.save(instrumento);
        LOGGER.info("Disponibilidad del instrumento actualizada. Instrumento no disponible.");

        PagoSalidaDto pagoSalidaDto = modelMapper.map(pagoGuardado, PagoSalidaDto.class);

        LOGGER.info("El pago ha sido ingresado correctamente con ID: {}", pagoGuardado.getId());

        return pagoSalidaDto;
    }

    @Override
    public Pago actualizarEstadoPago(String referenciaTransaccion, String nuevoEstado) {
        Pago pago = pagoRepository.findByReferenciaTransaccion(referenciaTransaccion).orElseThrow(() -> new RuntimeException("Pago no encontrado."));

        pago.setEstado(nuevoEstado);
        return pagoRepository.save(pago);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public List<PagoSalidaDto> listarPagos() {

        List<PagoSalidaDto> pagoSalidaDto = pagoRepository.findAll()
                .stream()
                .map(pago -> modelMapper.map(pago, PagoSalidaDto.class)
                )
                .toList();
        LOGGER.info("Listado de pagos: {}", JsonPrinter.toString(pagoSalidaDto));
        return pagoSalidaDto;
    }

    @Override
    @Secured("ROLE_USUARIO")
    public List<PagoSalidaDto> listarPagosDeUsuario(Long id){
        List<Pago> pagoSalidaDto = pagoRepository.findAllByUsuarioId(id);
        return pagoSalidaDto.stream()
                .map(pagoSalidaDto1 -> modelMapper.map(pagoSalidaDto1, PagoSalidaDto.class))
                .collect(Collectors.toList());
    }


    private String generarReferenciaTransaccion(){
        return UUID.randomUUID()
                .toString();
    }
}
