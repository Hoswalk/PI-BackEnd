package com.PI.Back.PIBackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "caracteristicas")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Caracteristica{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String icono;
    @ManyToOne
    @JoinColumn(name = "idInstrumento")
    private Instrumento instrumento;

}
