package org.fenc.puntodeventa.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaRequestDto {
    @Pattern(regexp = "^[0-9]{13}$", message = "El RUC debe tener 13 dígitos numéricos")
    private String ruc;

    @NotNull(message = "El ID de la persona es requerido")
    private Long idPersona;

    @NotNull(message = "El ID del tipo de pago es requerido")
    private Long idTipoPago;

    @NotNull(message = "La fecha es requerida")
    private LocalDate fecha;

    @PositiveOrZero(message = "El descuento no puede ser negativo")
    private Double descuento;


    @NotEmpty(message = "La factura debe tener al menos un item")
    private List<ItemFacturaRequestDto> items;
}