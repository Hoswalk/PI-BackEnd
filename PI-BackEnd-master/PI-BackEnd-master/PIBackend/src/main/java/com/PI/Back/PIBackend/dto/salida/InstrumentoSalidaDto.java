package com.PI.Back.PIBackend.dto.salida;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentoSalidaDto {

    private Long id;
    private String nombre;
    private String categoria;
    private Double precioDiario;
    private Integer stock;
    private List<String> imagenes;
    private String detalle;
    
    private String detalleview;

}
