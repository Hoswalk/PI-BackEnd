package com.PI.Back.PIBackend.controllers;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.entrada.UsuarioEntradaDto;
import com.PI.Back.PIBackend.dto.salida.InstrumentoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.dto.salida.UsuarioSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Pago;
import com.PI.Back.PIBackend.exceptions.ResourceNotFoundException;
import com.PI.Back.PIBackend.services.impl.AdminService;
import com.PI.Back.PIBackend.services.impl.InstrumentoService;
import com.PI.Back.PIBackend.services.impl.PagoService;
import com.PI.Back.PIBackend.services.impl.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UsuarioController {

    private final AdminService adminService;

    private final UsuarioService usuarioService;


    private final InstrumentoService instrumentoService;

    private final PagoService pagoService;

    @PostMapping("/pago")
    public ResponseEntity<PagoSalidaDto> procesarPago(@RequestBody PagoEntradaDto pagoEntradaDto){
        return new ResponseEntity<>(pagoService.procesarPago(pagoEntradaDto), HttpStatus.OK);
    }

    @GetMapping("/listaPagosDeUsuario/{id}")
    public ResponseEntity<List<PagoSalidaDto>> listarPagosPorUsuario(@PathVariable Long id){
        List<PagoSalidaDto> pagos = pagoService.listarPagosDeUsuario(id);
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/instrumentos")
    public ResponseEntity<List<InstrumentoSalidaDto>> listarInstrumentos(){
        return new ResponseEntity<>(adminService.listarInstrumentos(), HttpStatus.OK);
    }

    @GetMapping("/buscarInstrumento")
    public ResponseEntity<List<Instrumento>> buscarInstrumento(@RequestParam(required = false) String nombre, @RequestParam(required = false) String categoria){
        List<Instrumento> resultado = instrumentoService.buscarInstrumento(nombre, categoria);
        return ResponseEntity.ok(resultado);
    }

    //Pruebas
    @GetMapping("/buscarUsuarioPorId/{id}")
    public ResponseEntity<UsuarioSalidaDto> buscarUsuarioPorId(@PathVariable Long id){
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorId(id), HttpStatus.OK);
    }

    @PutMapping("/modificarUsuario/{id}")
    public ResponseEntity<UsuarioSalidaDto> modificarUsuario(@RequestBody UsuarioEntradaDto usuario, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.modificarUsuario(usuario, id), HttpStatus.OK);
    }
}
