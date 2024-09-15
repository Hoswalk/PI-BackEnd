package com.PI.Back.PIBackend.services;


import com.PI.Back.PIBackend.dto.entrada.CaracteristicaEntradaDto;
import com.PI.Back.PIBackend.dto.salida.CaracteristicaSalidaDto;
import jakarta.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface ICaracteristicaService {
    @Transactional
    List<CaracteristicaSalidaDto> listarCaracteristicas();

    @Transactional
    @Secured("ROLE_ADMIN")
    CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto caracteristica);
}
