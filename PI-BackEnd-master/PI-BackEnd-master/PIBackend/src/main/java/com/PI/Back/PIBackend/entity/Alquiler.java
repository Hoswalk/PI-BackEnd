package com.PI.Back.PIBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ALQUILERES")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "instrumento_id", nullable = false)
    private Instrumento instrumento;

    @ManyToOne
    @JoinColumn(name= "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precioTotal;
}
