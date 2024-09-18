package com.PI.Back.PIBackend.dto.salida;

import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CaracteristicaSalidaDto {

    private Long idCaracteristica;
    private String nombre;
    private String icono;
    private InstrumentoSalidaDto instrumentoSalidaDto;

}
