package com.PI.Back.PIBackend.services;


import com.PI.Back.PIBackend.entity.Instrumento;

import java.util.List;
import java.util.Optional;

public interface IInstrumentoService {

    Boolean actualizarEstado(Integer stock);
    List<Instrumento> buscarInstrumento(String nombre, String categoria);
    Optional<Instrumento> buscarInstrumentoPorId(Long id);

}
