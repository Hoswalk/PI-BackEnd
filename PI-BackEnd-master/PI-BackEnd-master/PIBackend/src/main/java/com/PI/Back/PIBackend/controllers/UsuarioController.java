package com.PI.Back.PIBackend.controllers;

import com.PI.Back.PIBackend.dto.entrada.PagoEntradaDto;
import com.PI.Back.PIBackend.dto.salida.PagoSalidaDto;
import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.services.impl.AdminService;
import com.PI.Back.PIBackend.services.impl.InstrumentoService;
import com.PI.Back.PIBackend.services.impl.PagoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private final InstrumentoService instrumentoService;

    private final PagoService pagoService;

    @PostMapping("/pago")
    public ResponseEntity<PagoSalidaDto> procesarPago(@RequestBody PagoEntradaDto pagoEntradaDto){
        return new ResponseEntity<>(pagoService.procesarPago(pagoEntradaDto), HttpStatus.OK);
    }

    @GetMapping("/buscarInstrumento")
    public ResponseEntity<List<Instrumento>> buscarInstrumento(@RequestParam(required = false) String nombre, @RequestParam(required = false) String categoria){
        List<Instrumento> resultado = instrumentoService.buscarInstrumento(nombre, categoria);
        return ResponseEntity.ok(resultado);
    }
}
