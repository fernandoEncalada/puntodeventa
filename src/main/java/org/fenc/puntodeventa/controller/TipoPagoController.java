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
import org.fenc.puntodeventa.dto.TipoPagoRequestDto;
import org.fenc.puntodeventa.model.TipoPago;
import org.fenc.puntodeventa.service.TipoPagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipo-pago")
@RequiredArgsConstructor
@Tag(name = "Tipo de Pago", description = "API para gestionar los tipos de pago")
public class TipoPagoController {
    private final TipoPagoService tipoPagoService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los tipos de pago",
        description = "Devuelve una lista de todos los tipos de pago disponibles en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de tipos de pago recuperada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoPago.class))
    )
    public List<TipoPago> getAll() {
        return tipoPagoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener tipo de pago por ID",
        description = "Busca y devuelve un tipo de pago específico según el ID proporcionado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tipo de pago encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoPago.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tipo de pago no encontrado",
            content = @Content
        )
    })
    public ResponseEntity<TipoPago> getById(@Parameter(description = "ID del tipo de pago") @PathVariable Long id) {
        Optional<TipoPago> tipoPago = tipoPagoService.findById(id);
        return tipoPago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo tipo de pago",
        description = "Crea un nuevo tipo de pago con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Tipo de pago creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TipoPago.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de tipo de pago inválidos",
            content = @Content
        )
    })
    public TipoPago create(@Valid @RequestBody TipoPagoRequestDto requestDto) {
        return tipoPagoService.save(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de pago", description = "Actualiza un tipo de pago existente por su ID")
    public ResponseEntity<TipoPago> update(@PathVariable Long id, @Valid @RequestBody TipoPagoRequestDto requestDto) {
        return tipoPagoService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de pago", description = "Elimina un tipo de pago existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (tipoPagoService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}