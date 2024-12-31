package org.fenc.puntodeventa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDto {
    @NotNull(message = "El stock es requerido")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El precio unitario es requerido")
    @Positive(message = "El precio unitario debe ser mayor que cero")
    private Double precioUnitario;

    @NotBlank(message = "La unidad no puede estar vacía")
    @Size(max = 20, message = "La unidad no puede exceder los 20 caracteres")
    private String unidad;

    @NotNull(message = "El ID de la clasificación es requerido")
    private Long idClasificacion;

    @NotNull(message = "El ID del proveedor es requerido")
    private Long idProveedor;

    private Boolean iva;

}
