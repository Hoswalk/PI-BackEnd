package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.services.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private InstrumentoRepository instrumentoRepository;


    @Override
    public Boolean alquilarInstrumento(InstrumentoSalidaDto instrumentoSalidaDto) {

        Long instrumentoId = instrumentoSalidaDto.getId();

        Optional<Instrumento> instrumentoOptional = instrumentoRepository.findById(instrumentoId);

        //verificar instrumento

        if (instrumentoOptional.isPresent()) {
            Instrumento instrumento = instrumentoOptional.get();

            //Verificar si hay stock
            if (instrumento.getStock() > 0){
                //Actualizar stock
                instrumento.setStock(instrumento.getStock() - 1);

                //Guardar
                instrumentoRepository.save(instrumento);

                // Registrar el alquiler en la base de datos si es necesario
                // alquilerRepository.save(new Alquiler(instrumento, instrumentoSalidaDto.getUsuarioId(), LocalDate.now()));

                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean devolverInstrumento(InstrumentoSalidaDto instrumentoSalidaDto) {
        return null;
    }
}
