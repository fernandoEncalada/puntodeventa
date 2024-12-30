package org.fenc.puntodeventa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequestDto {
    private String nombre;
    private String apellido;
    private String dni;
    private String celular;
    private String correo;
    private String user;
    private String password;
}
