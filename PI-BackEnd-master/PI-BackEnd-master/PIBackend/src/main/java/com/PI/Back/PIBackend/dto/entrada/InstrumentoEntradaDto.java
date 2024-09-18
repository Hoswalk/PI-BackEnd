package com.PI.Back.PIBackend.dto.entrada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentoEntradaDto {

    @NotNull(message = "El nombre no puede ser nulo.")
    @NotBlank(message = "Debe agregarse un nombre.")
    private String nombre;

    @NotNull
    @NotBlank(message = "La categoria no puede estar vacia.")
    private String categoria;

    @NotNull(message = "Debe especificarse el precio diario.")
    private Double precioDiario;


    @NotNull(message = "El producto debe contener minimo una imagen.")
    private List<String> imagenes;

    @NotNull
    @NotBlank(message = "El prodcuto debe incluir una descripcion.")
    private String detalle;

    @NotNull
    @NotBlank(message = "El prodcuto debe incluir una descripcion para la vista detallada.")
    private String detalleview;

    private boolean disponible;

}
