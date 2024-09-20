package com.PI.Back.PIBackend.services;

import com.PI.Back.PIBackend.dto.entrada.InstrumentoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.entity.Role;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAdminService {

    //INSTRUMENTO ADMIN
    InstrumentoSalidaDto registrarInstrumento(InstrumentoEntradaDto instrumento, List<String> imagenes);
    List<InstrumentoSalidaDto> listarInstrumentos();
    InstrumentoSalidaDto buscarInstrumentoPorId(Long id);
    void eliminarInstrumento(Long id) throws ResourceNotFoundException;
    InstrumentoSalidaDto modificarInstrumento(InstrumentoEntradaDto instrumentoEntradaDto, Long id) throws ResourceNotFoundException;
    InstrumentoSalidaDto agregarCategoria(Long id, String nuevaCategoria);


    //USUARIO ADMIN
    Usuario asignarRole(Long id, String role) throws ResourceNotFoundException;
    UsuarioSalidaDto buscarUsuarioPorId(Long id);
    void eliminarUsuario(Long id) throws ResourceNotFoundException;
    List<UsuarioSalidaDto> listarUsuarios();
}
