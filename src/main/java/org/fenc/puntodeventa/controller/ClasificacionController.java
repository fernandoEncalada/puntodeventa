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
import org.fenc.puntodeventa.dto.ClasificacionRequestDto;
import org.fenc.puntodeventa.model.Clasificacion;
import org.fenc.puntodeventa.service.ClasificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clasificacion")
@RequiredArgsConstructor
@Tag(name = "Clasificación", description = "API para gestionar las clasificaciones de productos")
public class ClasificacionController {
    private final ClasificacionService clasificacionService;

    @GetMapping
    @Operation(
            summary = "Obtener todas las clasificaciones",
            description = "Devuelve una lista de todas las clasificaciones disponibles en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de clasificaciones recuperada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clasificacion.class))
    )
    public List<Clasificacion> getAll() {
        return clasificacionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener clasificación por ID",
            description = "Busca y devuelve una clasificación específica según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clasificación encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clasificacion.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Clasificación no encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<Clasificacion> getById(
            @Parameter(description = "ID de la clasificación a buscar", required = true)
            @PathVariable Long id) {
        Optional<Clasificacion> clasificacion = clasificacionService.findById(id);
        return clasificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva clasificación",
            description = "Crea una nueva clasificación con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Clasificación creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clasificacion.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de clasificación inválidos",
                    content = @Content
            )
    })
    public Clasificacion create(
            @Parameter(description = "Datos de la clasificación a crear", required = true)
            @Valid @RequestBody ClasificacionRequestDto requestDto) {
        return clasificacionService.save(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una clasificación",
            description = "Actualiza una clasificación existente con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clasificación actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clasificacion.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Clasificación no encontrada",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de clasificación inválidos",
                    content = @Content
            )
    })
    public ResponseEntity<Clasificacion> update(
            @Parameter(description = "ID de la clasificación a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la clasificación", required = true)
            @Valid @RequestBody ClasificacionRequestDto requestDto) {
        return clasificacionService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una clasificación",
            description = "Elimina una clasificación existente según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clasificación eliminada exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Clasificación no encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la clasificación a eliminar", required = true)
            @PathVariable Long id) {
        if (clasificacionService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}