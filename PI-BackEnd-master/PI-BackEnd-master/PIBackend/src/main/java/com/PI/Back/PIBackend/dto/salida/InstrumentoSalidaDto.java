package com.PI.Back.PIBackend.dto.salida;


import lombok.*;

@Data
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

}
