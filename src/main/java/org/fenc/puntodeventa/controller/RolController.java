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
import org.fenc.puntodeventa.dto.RolRequestDto;
import org.fenc.puntodeventa.model.Rol;
import org.fenc.puntodeventa.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
@Tag(name = "Rol", description = "API para gestionar los roles de usuario")
public class RolController {
    private final RolService rolService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los roles",
        description = "Devuelve una lista de todos los roles disponibles en el sistema"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de roles recuperada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
    )
    public List<Rol> getAll() {
        return rolService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener rol por ID",
        description = "Busca y devuelve un rol específico según el ID proporcionado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Rol encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Rol no encontrado",
            content = @Content
        )
    })
    public ResponseEntity<Rol> getById(@Parameter(description = "ID del rol") @PathVariable Long id) {
        Optional<Rol> rol = rolService.findById(id);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo rol",
        description = "Crea un nuevo rol con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Rol creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de rol inválidos",
            content = @Content
        )
    })
    public Rol create(@Valid @RequestBody RolRequestDto request) {
        return rolService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un rol", description = "Actualiza un rol existente por su ID")
    public ResponseEntity<Rol> update(@PathVariable Long id, @Valid @RequestBody RolRequestDto request) {
        return rolService.update(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un rol", description = "Elimina un rol existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (rolService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/competencias")
    @Operation(summary = "Añadir competencias a un rol", description = "Añade una lista de competencias a un rol existente por su ID")
    public ResponseEntity<Rol> addCompetencia(@PathVariable Long id, @RequestBody List<Long> competenciasId) {
        return rolService.addCompetencia(id, competenciasId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/competencias/{idCompetencia}")
    @Operation(summary = "Remover una competencia", description = "Remueve una competencia por su ID")
    public ResponseEntity<Void> removeCompetencia(@PathVariable Long id, @PathVariable Long idCompetencia) {
        if (rolService.removeCompetencia(id, idCompetencia)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}