package com.PI.Back.PIBackend.dto.entrada;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaEntradaDto {
    @NotNull(message = "El nombre de la caracteristica no puede ser nula")
    private String nombre;

    @NotNull(message = "El logo no puede ser nulo")
    private String logo;

    @NotNull(message = "El id del instrumento no puede ser nulo")
    private Long idInstrumento;

}
