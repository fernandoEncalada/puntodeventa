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
import org.fenc.puntodeventa.dto.PersonaRequestDto;
import org.fenc.puntodeventa.model.Persona;
import org.fenc.puntodeventa.service.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persona")
@RequiredArgsConstructor
@Tag(name = "Persona", description = "API para gestionar las personas")
public class PersonaController {
    private final PersonaService personaService;

    @GetMapping
    @Operation(
            summary = "Obtener todas las personas",
            description = "Devuelve una lista de todas las personas registradas en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de personas recuperada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Persona.class))
    )
    public List<Persona> getAll() {
        return personaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener persona por ID",
            description = "Busca y devuelve una persona específica según el ID proporcionado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Persona encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Persona.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Persona no encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<Persona> getById(@Parameter(description = "ID de la persona") @PathVariable Long id) {
        Optional<Persona> persona = personaService.findById(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva persona",
            description = "Crea una nueva persona con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Persona creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Persona.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de persona inválidos",
                    content = @Content
            )
    })
    public Persona create(@Valid @RequestBody PersonaRequestDto requestDto) {
        return personaService.save(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una persona", description = "Actualiza una persona existente por su ID")
    public ResponseEntity<Persona> update(@PathVariable Long id, @Valid @RequestBody PersonaRequestDto requestDto) {
        return personaService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una persona", description = "Elimina una persona existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (personaService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}