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
import org.fenc.puntodeventa.dto.ProductoRequestDto;
import org.fenc.puntodeventa.model.Producto;
import org.fenc.puntodeventa.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
@Tag(name = "Producto", description = "API para gestionar los productos")
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los productos",
        description = "Devuelve una lista de todos los productos disponibles en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de productos recuperada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
    )
    public List<Producto> getAll(@RequestHeader("X-Content-Type-Options") String header) {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener producto por ID",
        description = "Busca y devuelve un producto específico según el ID proporcionado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    public ResponseEntity<Producto> getById(@Parameter(description = "ID del producto") @PathVariable Long id,
                                            @RequestHeader("X-Content-Type-Options") String header) {
        Optional<Producto> producto = productoService.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Crea un nuevo producto con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de producto inválidos",
            content = @Content
        )
    })
    public Producto create(@Valid @RequestBody ProductoRequestDto requestDto) {
        return productoService.save(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza un producto existente por su ID")
    public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody ProductoRequestDto requestDto) {
        return productoService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productoService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}