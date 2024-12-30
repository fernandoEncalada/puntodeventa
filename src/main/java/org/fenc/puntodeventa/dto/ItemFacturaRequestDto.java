package org.fenc.puntodeventa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemFacturaRequestDto {
    private Long idProducto;
    private int cantidad;
}