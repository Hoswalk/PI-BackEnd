package com.PI.Back.PIBackend.dto.entrada;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaEntradaDto {
    @NotNull(message = "El nombre de la caracteristica no puede ser nula")
    private String nombre;

    @NotNull(message = "El icono no puede ser nulo")
    private String icono;

    @NotNull(message = "El id del instrumento no puede ser nulo")
    private Long idInstrumento;

}
