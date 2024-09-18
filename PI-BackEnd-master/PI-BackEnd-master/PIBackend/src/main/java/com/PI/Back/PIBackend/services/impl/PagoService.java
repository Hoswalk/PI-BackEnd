package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Alquiler;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Pago;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.repository.AlquilerRepository;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.repository.PagoRepository;
import com.PI.Back.PIBackend.repository.UsuarioRepository;
import com.PI.Back.PIBackend.services.IPagoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public PagoSalidaDto procesarPago(PagoEntradaDto pagoEntradaDto) {

        LOGGER.info("Procesando pago para alquilerId: {}", pagoEntradaDto.getInstrumentoId());

        //COMENTADO PARA PRUEBA
        //Alquiler alquiler = alquilerRepository.findById(pagoEntradaDto.getAlquilerId())
        //        .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

        Instrumento instrumento = instrumentoRepository.findById(pagoEntradaDto.getInstrumentoId()).orElseThrow(() -> new RuntimeException("Instrumento no encontrado"));

        Usuario usuario = usuarioRepository.findById(pagoEntradaDto.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //Verifiacion de disponibilidad

        if(!alquilerService.instrumentoDisponible(instrumento.getId(), pagoEntradaDto.getFechaInicio(), pagoEntradaDto.getFechaFin())){
            throw new RuntimeException("El insturmento no esta disponible en la fecha seleccionada.");
        }

        //Creando un alquiler en db

        Alquiler alquiler = new Alquiler();

        alquiler.setInstrumento(instrumento);
        alquiler.setUsuario(usuario);
        alquiler.setFechaInicio(pagoEntradaDto.getFechaInicio());
        alquiler.setFechaFin(pagoEntradaDto.getFechaFin());
        alquiler.setMonto(pagoEntradaDto.getMonto());

        Alquiler alquilerGuardado = alquilerRepository.save(alquiler);


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

        Pago pagoGuardado = pagoRepository.save(pagoIngresado);

        //Actualizar disponibilidad

        instrumento.setDisponible(false);
        instrumentoRepository.save(instrumento);

        PagoSalidaDto pagoSalidaDto = modelMapper.map(pagoGuardado, PagoSalidaDto.class);

        LOGGER.info("El pago ha sido ingresado correctamente.");

        return pagoSalidaDto;
    }

    @Override
    public Pago actualizarEstadoPago(String referenciaTransaccion, String nuevoEstado) {
        Pago pago = pagoRepository.findByReferenciaTransaccion(referenciaTransaccion).orElseThrow(() -> new RuntimeException("Pago no encontrado."));

        pago.setEstado(nuevoEstado);
        return pagoRepository.save(pago);
    }

    private String generarReferenciaTransaccion(){
        return UUID.randomUUID()
                .toString();
    }
}
