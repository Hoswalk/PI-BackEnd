package com.PI.Back.PIBackend.entity;



import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "alquiler_id", nullable = false)
    private Alquiler alquiler;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "instrumento_id")
    private Instrumento instrumento;

    private Double monto;

    private LocalDate fechaDePago;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private String metodoDePago;

    private String estado;

    private String referenciaTransaccion;
}
