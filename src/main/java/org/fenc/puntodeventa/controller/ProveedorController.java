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
import org.fenc.puntodeventa.dto.ProveedorRequestDto;
import org.fenc.puntodeventa.model.Proveedor;
import org.fenc.puntodeventa.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedor")
@RequiredArgsConstructor
@Tag(name = "Proveedor", description = "API para gestionar los proveedores")
public class ProveedorController {
    private final ProveedorService proveedorService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los proveedores",
            description = "Devuelve una lista de todos los proveedores registrados en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de proveedores recuperada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))
    )
    public List<Proveedor> getAll() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener proveedor por ID",
            description = "Busca y devuelve un proveedor específico según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Proveedor encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Proveedor no encontrado",
                    content = @Content
            )
    })
    public ResponseEntity<Proveedor> getById(@Parameter(description = "ID del proveedor") @PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorService.findById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo proveedor",
            description = "Crea un nuevo proveedor con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Proveedor creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de proveedor inválidos",
                    content = @Content
            )
    })
    public Proveedor create(@Valid @RequestBody ProveedorRequestDto requestDto) {
        return proveedorService.save(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un proveedor", description = "Actualiza un proveedor existente por su ID")
    public ResponseEntity<Proveedor> update(@PathVariable Long id, @Valid @RequestBody ProveedorRequestDto requestDto) {
        return proveedorService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un proveedor", description = "Elimina un proveedor existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (proveedorService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}