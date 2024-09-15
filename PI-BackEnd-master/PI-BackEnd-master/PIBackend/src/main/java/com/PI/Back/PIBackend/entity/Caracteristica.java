package com.PI.Back.PIBackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "caracteristicas")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Caracteristica{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCaracteristica;
    private String nombre;
    private String logo;
    @ManyToOne
    @JoinColumn(name = "idInstrumento")
    private Instrumento instrumento;

}
