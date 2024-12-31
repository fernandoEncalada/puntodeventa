package org.fenc.puntodeventa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class TipoPagoRequestDto {
        @NotBlank(message = "El tipo no puede estar vacío")
    @Size(min = 2, max = 50, message = "El tipo debe tener entre 2 y 50 caracteres")
    private String tipo;
    
    @NotBlank(message = "La descripción no puede estar vacío")
    @Size(min = 2, max = 50, message = "La descripción debe tener entre 2 y 50 caracteres")
    private String descripcion;
}
