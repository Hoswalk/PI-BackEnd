package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.services.IInstrumentoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstrumentoService implements IInstrumentoService {

    private final Logger LOGGER = LoggerFactory.getLogger(InstrumentoService.class);

    @Autowired
    private InstrumentoRepository instrumentoRepository;


    @Override
    public Boolean actualizarEstado(Integer stock) {
        return null;
    }

    @Override
    public List<Instrumento> buscarInstrumento(String nombre, String categoria) {
        if (nombre != null && !nombre.isEmpty() && categoria == null){
            return instrumentoRepository.findByNombre(nombre);
        } else if (categoria != null && !categoria.isEmpty() && nombre == null) {
            return instrumentoRepository.findByCategoria(categoria);
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Instrumento> buscarInstrumentoPorId(Long id) {
        return instrumentoRepository.findById(id);
    }
}
