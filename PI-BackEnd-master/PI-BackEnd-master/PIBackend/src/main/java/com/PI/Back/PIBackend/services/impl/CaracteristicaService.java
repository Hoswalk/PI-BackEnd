package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.entrada.CaracteristicaEntradaDto;
import com.PI.Back.PIBackend.dto.salida.CaracteristicaSalidaDto;
import com.PI.Back.PIBackend.entity.Caracteristica;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.repository.CaracteristicaRepository;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.services.ICaracteristicaService;
import com.PI.Back.PIBackend.utils.JsonPrinter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaracteristicaService implements ICaracteristicaService {

    private final Logger LOGGER = LoggerFactory.getLogger(CaracteristicaService.class);
    private final CaracteristicaRepository caracteristicaRepository;
    private final InstrumentoRepository instrumentoRepository;
    private final ModelMapper modelMapper;
    @Transactional
    @Override
    public List<CaracteristicaSalidaDto> listarCaracteristicas() {

        List<CaracteristicaSalidaDto> caracteristicaSalidaDto = caracteristicaRepository.findAll()
                .stream()
                .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaSalidaDto.class)
                )
                .toList();

        LOGGER.info("Lista de caracteristicas registradas: {}", JsonPrinter.toString(caracteristicaSalidaDto));
        return caracteristicaSalidaDto;
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    @Override
    public CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto caracteristica) throws ResourceNotFoundException {
        // Busca el instrumento en la base de datos por su id
        Instrumento instrumento = instrumentoRepository.findById(caracteristica.getIdInstrumento())
                .orElseThrow(() -> new ResourceNotFoundException("Instrumento al que se le quiere asignar la caracteristica no encontrado"));



        Caracteristica caracteristicaGuardada = caracteristicaRepository.save(modelMapper.map(caracteristica, Caracteristica.class));
        caracteristicaGuardada.setInstrumento(instrumento);
        CaracteristicaSalidaDto caracteristicaSalidaDto = modelMapper.map(caracteristicaGuardada, CaracteristicaSalidaDto.class);
        //Log de la salida
        LOGGER.info("Caracteristica guardado: {}", caracteristicaSalidaDto);
        return caracteristicaSalidaDto;
    }

}
