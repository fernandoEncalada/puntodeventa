package org.fenc.puntodeventa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaRequestDto {
    private String ruc;
    private Long idPersona;
    private Long idTipoPago;
    private String fecha;
    private double descuento;
    private List<ItemFacturaRequestDto> items;

    // Getters y setters
}