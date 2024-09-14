package com.PI.Back.PIBackend.dto.salida;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentoSalidaDto {

    private Long id;

    private String nombre;

    private String categoria;

    private Double precioDiario;

    private Integer stock;

    private String imagen;

    private String detalle;
    
    private String detalleview;

}
