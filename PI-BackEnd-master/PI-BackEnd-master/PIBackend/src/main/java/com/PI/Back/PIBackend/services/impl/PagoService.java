package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Alquiler;
import com.PI.Back.PIBackend.entity.Pago;
import com.PI.Back.PIBackend.repository.AlquilerRepository;
import com.PI.Back.PIBackend.repository.PagoRepository;
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
    private AlquilerRepository alquilerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Secured("ROLE_USUARIO")
    public PagoSalidaDto procesarPago(PagoEntradaDto pagoEntradaDto) {

        Alquiler alquiler = alquilerRepository.findById(pagoEntradaDto.getAlquilerId())
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

        Pago pagoIngresado = modelMapper.map(pagoEntradaDto, Pago.class);

        pagoIngresado.setFechaDePago(LocalDate.now());
        pagoIngresado.setReferenciaTransaccion(generarReferenciaTransaccion());
        pagoIngresado.setEstado("PENDIENTE");
        pagoIngresado.setAlquiler(alquiler);

        Pago pagoGuardado = pagoRepository.save(pagoIngresado);

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
