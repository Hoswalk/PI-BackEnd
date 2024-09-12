package com.PI.Back.PIBackend.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoEntradaDto {

    @NotNull
    private Double monto;

    @NotBlank(message = "Se debe elegir un metodo de pago.")
    private String metodoPago;

    @NotNull
    private Long alquilerId;
}
