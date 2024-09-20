package com.PI.Back.PIBackend.services;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Pago;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPagoService {

    PagoSalidaDto procesarPago(PagoEntradaDto pagoEntradaDto);

    Pago actualizarEstadoPago(String referenciaTransaccion, String nuevoEstado);

    List<PagoSalidaDto> listarPagos();

    List<PagoSalidaDto> listarPagosDeUsuario(Long id) throws ResourceNotFoundException;
}
