package org.fenc.puntodeventa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.FacturaRequestDto;
import org.fenc.puntodeventa.model.Factura;
import org.fenc.puntodeventa.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/factura")
@RequiredArgsConstructor
@Tag(name = "Factura", description = "API para gestionar las facturas")
public class FacturaController {
    private final FacturaService facturaService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las facturas",
        description = "Devuelve una lista de todas las facturas registradas en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de facturas recuperada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))
    )
    public List<Factura> getAll() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener factura por ID",
        description = "Busca y devuelve una factura específica según el ID proporcionado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Factura encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Factura no encontrada",
            content = @Content
        )
    })
    public ResponseEntity<Factura> getById(@Parameter(description = "ID de la factura") @PathVariable Long id) {
        Optional<Factura> factura = facturaService.findById(id);
        return factura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear una nueva factura",
        description = "Crea una nueva factura con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Factura creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Factura.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de factura inválidos",
            content = @Content
        )
    })
    public ResponseEntity<Factura> create(@Valid @RequestBody FacturaRequestDto facturaRequestDto) {
        try {
            Factura factura = facturaService.createFactura(facturaRequestDto);
            return ResponseEntity.ok(factura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una factura", description = "Elimina una factura existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (facturaService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}