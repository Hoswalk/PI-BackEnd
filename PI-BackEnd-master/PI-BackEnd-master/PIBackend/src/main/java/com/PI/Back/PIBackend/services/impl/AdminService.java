package com.PI.Back.PIBackend.services.impl;


import com.PI.Back.PIBackend.dto.entrada.InstrumentoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Role;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.repository.UsuarioRepository;
import com.PI.Back.PIBackend.services.IAdminService;
import com.PI.Back.PIBackend.utils.JsonPrinter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {
    private final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private InstrumentoRepository instrumentoRepository;

    private final ModelMapper modelMapper;


    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public InstrumentoSalidaDto registrarInstrumento(InstrumentoEntradaDto instrumento) {
        Instrumento instrumentoGuardado = instrumentoRepository.save(modelMapper.map(instrumento, Instrumento.class));
        InstrumentoSalidaDto instrumentoSalidaDto = modelMapper.map(instrumentoGuardado, InstrumentoSalidaDto.class);
        //Log de la salida
        LOGGER.info("Instrumento guardado: {}", instrumentoSalidaDto);
        return instrumentoSalidaDto;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public List<InstrumentoSalidaDto> listarInstrumentos() {

        List<InstrumentoSalidaDto> instrumentoSalidaDto = instrumentoRepository.findAll()
                .stream()
                .map(instrumento -> modelMapper.map(instrumento, InstrumentoSalidaDto.class)
                )
                .toList();

        LOGGER.info("Listado de todos los instrumentos: {}", JsonPrinter.toString(instrumentoSalidaDto));

        return instrumentoSalidaDto;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public InstrumentoSalidaDto buscarInstrumentoPorId(Long id) {
        Instrumento instrumentoBuscado = instrumentoRepository.findById(id).orElse(null);
        InstrumentoSalidaDto instrumentoEncontrado = null;

        if (instrumentoBuscado != null){
            instrumentoEncontrado = modelMapper.map(instrumentoBuscado, InstrumentoSalidaDto.class);
            LOGGER.info("Instrumento encontrado: {}", JsonPrinter.toString(instrumentoEncontrado));
        } else LOGGER.error("Instrumento no encontrado: {}", JsonPrinter.toString(instrumentoBuscado));
        return instrumentoEncontrado;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public InstrumentoSalidaDto agregarCategoria(Long id, String nuevaCategoria) {
        Instrumento instrumento = instrumentoRepository.findById(id).orElseThrow(() -> new ResolutionException("Instrumento con id: " + id + "no encontrado"));

        instrumento.setCategoria(nuevaCategoria);

        instrumentoRepository.save(instrumento);

        return new InstrumentoSalidaDto(
                instrumento.getId(),
                instrumento.getNombre(),
                instrumento.getCategoria(),
                instrumento.getPrecioDriario(),
                instrumento.getStock(),
                instrumento.getImagen(),
                instrumento.getDetalle()
        );
    }

    @Override
    public void eliminarInstrumento(Long id) throws ResourceNotFoundException {
        if(buscarInstrumentoPorId(id) != null){
            instrumentoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el instrumento con id: {}", + id);
        } else {
            throw new ResourceNotFoundException("No se ha encontrado ningun instrumento con el ID: " + id);
        }
    }

    @Override
    @Secured("ROLE_ADMIN")
    public InstrumentoSalidaDto modificarInstrumento(InstrumentoEntradaDto instrumentoEntradaDto, Long id) throws ResourceNotFoundException {

        Instrumento instrumentoIngresado = modelMapper.map(instrumentoEntradaDto, Instrumento.class);
        Instrumento instrumentoActualizar = instrumentoRepository.findById(id).orElse(null);

        InstrumentoSalidaDto instrumentoSalidaDto;

        if (instrumentoActualizar != null){
            instrumentoActualizar.setNombre(instrumentoIngresado.getNombre());
            instrumentoActualizar.setCategoria(instrumentoIngresado.getCategoria());
            instrumentoActualizar.setPrecioDriario(instrumentoIngresado.getPrecioDriario());
            instrumentoActualizar.setStock(instrumentoIngresado.getStock());
            instrumentoActualizar.setImagen(instrumentoIngresado.getImagen());
            instrumentoActualizar.setDetalle(instrumentoIngresado.getDetalle());

            instrumentoRepository.save(instrumentoActualizar);

            instrumentoSalidaDto = modelMapper.map(instrumentoActualizar, InstrumentoSalidaDto.class);
        } else {
            LOGGER.error("No fue posible actualizar el instrumento porque no se encuentra en la base de datos");
            throw new ResourceNotFoundException("No es posible actualizar el instrumento con ID: " + id + "ya que no se encuentra en la base de datos");
        }
        return instrumentoSalidaDto;
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void asignarRole(String email, Role newRole) throws ResourceNotFoundException {
        //Obtener email
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        //Verificar administrador

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario currentUser = (Usuario) authentication.getPrincipal();

        if (!currentUser.getRole().equals(Role.ADMIN)){
            throw new SecurityException("Este usuario no tiene los permisos para asignar un nuevo rol");
        }

        //Asignar un nuevo rol
        usuario.setRole(newRole);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    //@Secured("ROLE_ADMIN")
    public List<UsuarioSalidaDto> listarUsuarios() {

        List<UsuarioSalidaDto> usuarioSalidaDto = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioSalidaDto.class)
                )
                .toList();

        LOGGER.info("Lista de usuarios registrados: {}", JsonPrinter.toString(usuarioSalidaDto));
        return usuarioSalidaDto;
    }


}