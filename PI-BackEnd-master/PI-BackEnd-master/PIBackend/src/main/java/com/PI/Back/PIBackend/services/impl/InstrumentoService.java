package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.services.IInstrumentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrumentoService implements IInstrumentoService {

    private final Logger LOGGER = LoggerFactory.getLogger(InstrumentoService.class);

    private InstrumentoRepository instrumentoRepository;


    @Override
    public Boolean actualizarEstado(Integer stock) {
        return null;
    }
}
