package com.PI.Back.PIBackend.dto.salida;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class CaracteristicaSalidaDto {

    private Long id;
    private String nombre;
    private String icono;
    private InstrumentoSalidaDto instrumentoSalidaDto;

}
