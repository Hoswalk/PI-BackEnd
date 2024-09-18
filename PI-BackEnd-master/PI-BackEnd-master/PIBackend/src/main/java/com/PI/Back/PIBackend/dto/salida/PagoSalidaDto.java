package com.PI.Back.PIBackend.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagoSalidaDto {

    private Long id;
    private Long alquilerId;
    private Long usuarioId;
    private Double monto;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private LocalDate fechaDePago;
    private String estado;
    private String referenciaTransaccion;
}
