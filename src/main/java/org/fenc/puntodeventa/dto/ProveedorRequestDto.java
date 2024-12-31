package org.fenc.puntodeventa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class ProveedorRequestDto {
    @NotBlank(message = "El RUC no puede estar vacío")
    @Pattern(regexp = "^[0-9]{13}$", message = "El RUC debe tener 11 dígitos numéricos")
    private String ruc;

    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos numéricos")
    private String telefono;

    @NotBlank(message = "El país no puede estar vacío")
    @Size(max = 50, message = "El país no puede exceder los 50 caracteres")
    private String pais;

    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La moneda no puede estar vacía")
    @Size(max = 10, message = "La moneda no puede exceder los 10 caracteres")
    private String moneda;
}
