package com.PI.Back.PIBackend.controllers;

import com.PI.Back.PIBackend.dto.entrada.CaracteristicaEntradaDto;
import com.PI.Back.PIBackend.dto.entrada.InstrumentoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.CaracteristicaSalidaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.entity.Role;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.services.impl.AdminService;
import com.PI.Back.PIBackend.services.impl.CaracteristicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UsuarioAdminController {

    private final AdminService adminService;
    private final CaracteristicaService caracteristicaService;


    //INSTRUMENTOS

    @PostMapping("/instrumento/registrarInstrumento")
    public ResponseEntity<InstrumentoSalidaDto> registrarInstrumento(@Valid @RequestBody InstrumentoEntradaDto instrumento){

        return new ResponseEntity<>(adminService.registrarInstrumento(instrumento, instrumento.getImagenes()), HttpStatus.CREATED);
    }

    @GetMapping("/instrumento/buscarInstrumentoId/{id}")
    public ResponseEntity<InstrumentoSalidaDto> buscarInstrumentoId(@PathVariable Long id){
        return new ResponseEntity<>(adminService.buscarInstrumentoPorId(id), HttpStatus.OK);
    }

    /*
    @GetMapping("/instrumentos")
    public ResponseEntity<List<InstrumentoSalidaDto>> listarInstrumentos(){
        return new ResponseEntity<>(adminService.listarInstrumentos(), HttpStatus.OK);
    }
     */

    @PutMapping("/instrumento/modificarInstrumento/{id}")
    public ResponseEntity<InstrumentoSalidaDto> modeificarInstrumento(@RequestBody InstrumentoEntradaDto instrumento, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(adminService.modificarInstrumento(instrumento, id), HttpStatus.OK);
    }

    @PutMapping("/instrumento/{id}/agregarCategoria")
    public ResponseEntity<InstrumentoSalidaDto> actualizarCategoria(@PathVariable Long id, @RequestBody String nuevaCategoria){
        InstrumentoSalidaDto instrumentoActualizado = adminService.agregarCategoria(id, nuevaCategoria);
        return ResponseEntity.ok(instrumentoActualizado);
    }

    @DeleteMapping("/instrumento/eliminar/{id}")
    public ResponseEntity<?> eliminarIntrumento(@PathVariable Long id) throws ResourceNotFoundException {
        adminService.eliminarInstrumento(id);
        return new ResponseEntity<>("Instrumento eliminado correctamente", HttpStatus.NO_CONTENT);
    }


    //USUARIO

    @PutMapping("/modificarRole/{email}/role")
    public ResponseEntity<?> asignarRole(@PathVariable String email, @RequestBody Role newRole) throws ResourceNotFoundException {
        //Role role = Role.valueOf(String.valueOf(newRole));
        adminService.asignarRole(email, newRole);
        return ResponseEntity.ok("Se ha asignado el rol correctamente");
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioSalidaDto>> listarUsuarios(){
        return new ResponseEntity<>(adminService.listarUsuarios(), HttpStatus.OK);
    }


    // CACACTERISTICAS
    @GetMapping("/caracteristicas")
    public ResponseEntity<List<CaracteristicaSalidaDto>> listarCaracteristicas(){
        return new ResponseEntity<>(caracteristicaService.listarCaracteristicas(), HttpStatus.OK);
    }

    @PostMapping("/instrumento/registrarCaracteristica")
    public ResponseEntity<CaracteristicaSalidaDto> registrarCaracteristica(@RequestBody CaracteristicaEntradaDto caracteristica) throws ResourceNotFoundException {
        return new ResponseEntity<>(caracteristicaService.registrarCaracteristica(caracteristica), HttpStatus.CREATED);
    }



}