package com.PI.Back.PIBackend.services;

import java.time.LocalDate;

public interface IAlquilerService {

    public boolean instrumentoDisponible(Long instrumentoId, LocalDate fechaInicio, LocalDate fechaFin);
}
