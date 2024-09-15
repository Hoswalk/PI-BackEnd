package com.PI.Back.PIBackend.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CaracteristicaSalidaDto {
    private Long idCaracteristica;
    private String nombre;
    private String logo;
    private InstrumentoSalidaDto instrumentoSalidaDto;


}
