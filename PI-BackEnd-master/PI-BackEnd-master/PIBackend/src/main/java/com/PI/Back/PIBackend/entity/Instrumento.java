package com.PI.Back.PIBackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INSTRUMENTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String categoria;
    private Double precioDriario;
    private Integer stock;
    private String imagen;
    private String detalle;

}
