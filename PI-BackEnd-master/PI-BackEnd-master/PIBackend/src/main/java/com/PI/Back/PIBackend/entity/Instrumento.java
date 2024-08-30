package com.PI.Back.PIBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "INSTRUMENTOS")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String categoria;
    private String proovedor;
    private Double precioDriario;
    private Integer stock;
    private String imagen;
    private String detalle;

}
