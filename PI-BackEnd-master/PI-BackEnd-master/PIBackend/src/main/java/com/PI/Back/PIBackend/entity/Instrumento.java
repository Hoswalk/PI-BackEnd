package com.PI.Back.PIBackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private Double precioDiario;
    //HAY QUE BORRAR STOCK
     private Integer stock;

    @ElementCollection
    private List<String> imagenes;

    private String detalle;
    private String detalleview;

}
