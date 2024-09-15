package com.PI.Back.PIBackend.services;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoService {

    PagoSalidaDto procesarPago(PagoEntradaDto pagoEntradaDto);

    Pago actualizarEstadoPago(String referenciaTransaccion, String nuevoEstado);

}
