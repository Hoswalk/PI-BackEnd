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

    private Double monto;

    private LocalDate fechaDePago;

    private String metodoDePago;

    private String estado;

    private String referenciaTransaccion;
}
