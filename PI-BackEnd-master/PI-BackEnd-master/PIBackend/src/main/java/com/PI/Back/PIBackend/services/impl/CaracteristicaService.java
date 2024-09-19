package com.PI.Back.PIBackend.services.impl;

import  com.PI.Back.PIBackend.dto.entrada.CaracteristicaEntradaDto;
import com.PI.Back.PIBackend.dto.salida.CaracteristicaSalidaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaracteristicaService implements ICaracteristicaService {

    private final Logger LOGGER = LoggerFactory.getLogger(CaracteristicaService.class);
    private final CaracteristicaRepository caracteristicaRepository;
    private final InstrumentoRepository instrumentoRepository;
    private final ModelMapper modelMapper;
    private final InstrumentoService instrumentoService;

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
        CaracteristicaSalidaDto caracteristicaSalidaDto = entidadADtoSalida(caracteristicaGuardada);
        caracteristicaGuardada.setInstrumento(instrumento);
     //   CaracteristicaSalidaDto caracteristicaSalidaDto = modelMapper.map(caracteristicaGuardada, CaracteristicaSalidaDto.class);
        //Log de la salida
     //   LOGGER.info("Caracteristica guardado: {}", caracteristicaSalidaDto);
        return caracteristicaSalidaDto;
    }













    private CaracteristicaSalidaDto entidadADtoSalida(Caracteristica caracteristica) {
        CaracteristicaSalidaDto caracteristicaSalidaDto = modelMapper.map(caracteristica, CaracteristicaSalidaDto.class);

        LOGGER.info("Id del instrumento a convertir {}: " , caracteristica.getInstrumento().getId());

        Optional<Instrumento> instrumentoParaMapear = instrumentoRepository.findById(caracteristica.getInstrumento().getId());

        // Verifica si el instrumento está presente
        if (instrumentoParaMapear.isPresent()) {
            Instrumento instrumento = instrumentoParaMapear.get();
            LOGGER.info("Instrumento a Mapear obtenido de la BD con el id :{} " , instrumento);

            // Mapear el objeto Instrumento al DTO
            InstrumentoSalidaDto instrumentoParaSetear = modelMapper.map(instrumento, InstrumentoSalidaDto.class);
            LOGGER.info("Instrumento a setear {}: " , instrumentoParaSetear);

            caracteristicaSalidaDto.setInstrumentoSalidaDto(instrumentoParaSetear);
        } else {
            LOGGER.warn("Instrumento no encontrado para el id: {}", caracteristica.getInstrumento().getId());
        }

        return caracteristicaSalidaDto;
    }

}
