package org.fenc.puntodeventa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RolRequestDto {
    @NotBlank(message = "El rol no puede estar vacío")
    @Size(min = 3, max = 50, message = "El rol debe tener entre 3 y 50 caracteres")
    private String rol;
    private Boolean estado;
    private List<Long> competenciasId;
}
