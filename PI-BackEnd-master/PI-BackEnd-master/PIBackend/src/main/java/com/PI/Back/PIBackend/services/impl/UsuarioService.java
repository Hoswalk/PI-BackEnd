package com.PI.Back.PIBackend.services.impl;

import com.PI.Back.PIBackend.dto.entrada.UsuarioEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Usuario;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.repository.InstrumentoRepository;
import com.PI.Back.PIBackend.repository.UsuarioRepository;
import com.PI.Back.PIBackend.services.IUsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private InstrumentoRepository instrumentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;


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

    @Override
    @Transactional
    @Secured("ROLE_USUARIO, ROLE_ADMIN")
    public UsuarioSalidaDto modificarUsuario(UsuarioEntradaDto usuarioEntradaDto, Long id) throws ResourceNotFoundException {

        Usuario usuarioIngresado = modelMapper.map(usuarioEntradaDto, Usuario.class);
        Usuario usuarioActualizar = usuarioRepository.findById(id).orElse(null);

        UsuarioSalidaDto usuarioSalidaDto;

        if (usuarioActualizar != null){
            usuarioActualizar.setEmail(usuarioIngresado.getEmail());
            usuarioActualizar.setCelular(usuarioIngresado.getCelular());
            usuarioActualizar.setDireccion(usuarioIngresado.getDireccion());

            // Log antes de guardar
            LOGGER.info("Actualizando usuario con ID: " + id + " con los nuevos datos: " + usuarioActualizar);

            usuarioRepository.save(usuarioActualizar);

            // Log después de guardar
            LOGGER.info("Usuario con ID: " + id + " actualizado exitosamente.");

            usuarioSalidaDto = modelMapper.map(usuarioActualizar, UsuarioSalidaDto.class);
        } else {
            LOGGER.error("No fue posible actualizar los datos del usuario ya que no se encuentra en la base de datos");
            throw new ResourceNotFoundException("No es posible actualizar el usuario con ID: " + id + "ya que no se encuentra en la base de datos");
        }

        return usuarioSalidaDto;
    }
}
