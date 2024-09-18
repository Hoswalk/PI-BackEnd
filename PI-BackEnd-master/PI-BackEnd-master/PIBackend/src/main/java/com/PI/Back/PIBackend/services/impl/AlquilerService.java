package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.repository.AlquilerRepository;
import com.PI.Back.PIBackend.services.IAlquilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@RequiredArgsConstructor
public class AlquilerService implements IAlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Override
    public boolean instrumentoDisponible(Long instrumentoId, LocalDate fechaInicio, LocalDate fechaFin) {
        return alquilerRepository.findConflictingAlquiler(instrumentoId, fechaInicio, fechaFin).isEmpty();
    }
}
