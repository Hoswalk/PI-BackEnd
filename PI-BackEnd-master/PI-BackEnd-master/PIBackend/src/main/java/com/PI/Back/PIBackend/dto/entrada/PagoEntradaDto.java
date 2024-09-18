package com.PI.Back.PIBackend.dto.entrada;

import com.PI.Back.PIBackend.entity.Instrumento;
import com.PI.Back.PIBackend.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoEntradaDto {

    @NotNull
    private Double monto;

    @NotBlank(message = "Se debe elegir un metodo de pago.")
    private String metodoPago;

    @NotNull
    private Long instrumentoId;

    @NotNull
    private Long usuarioId;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;
}
