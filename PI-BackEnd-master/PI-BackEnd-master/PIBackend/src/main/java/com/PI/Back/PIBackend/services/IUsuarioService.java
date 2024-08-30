package com.PI.Back.PIBackend.services;

import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;

public interface IUsuarioService {

    Boolean alquilarInstrumento(InstrumentoSalidaDto instrumentoSalidaDto);

    Boolean devolverInstrumento(InstrumentoSalidaDto instrumentoSalidaDto);
}
