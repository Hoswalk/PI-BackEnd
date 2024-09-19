package com.PI.Back.PIBackend.services;

import com.PI.Back.PIBackend.dto.entrada.UsuarioEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;

public interface IUsuarioService {

    Boolean alquilarInstrumento(InstrumentoSalidaDto instrumentoSalidaDto);

    Boolean devolverInstrumento(InstrumentoSalidaDto instrumentoSalidaDto);

    UsuarioSalidaDto buscarUsuarioPorId(Long id);

    UsuarioSalidaDto modificarUsuario(UsuarioEntradaDto usuarioEntradaDto, Long id) throws ResourceNotFoundException;
}
