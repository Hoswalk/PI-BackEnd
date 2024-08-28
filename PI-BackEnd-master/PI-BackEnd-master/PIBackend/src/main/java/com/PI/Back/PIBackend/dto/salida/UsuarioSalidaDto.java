package com.PI.Back.PIBackend.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSalidaDto {

    private long id;

    private String nombre;

    private String apellido;

    private String email;
    private String direccion;
    private String celular;

}
