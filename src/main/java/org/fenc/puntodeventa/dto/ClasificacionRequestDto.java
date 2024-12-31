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
public class ClasificacionRequestDto {
    @NotBlank(message = "El grupo no puede estar vacío")
    @Size(min = 3, max = 100, message = "El grupo debe tener entre 3 y 100 caracteres")
    private String grupo;
}
