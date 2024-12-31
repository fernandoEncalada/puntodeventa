package org.fenc.puntodeventa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.CompetenciaRequestDto;
import org.fenc.puntodeventa.model.Competencia;
import org.fenc.puntodeventa.service.CompetenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competencia")
@RequiredArgsConstructor
@Tag(name = "Competencia", description = "API de Competencia")
public class CompetenciaController {
    private final CompetenciaService competenciaService;

    @GetMapping
    @Operation(summary = "Obtener todas las competencias", description = "Devuelve una lista de todas las competencias")
    public List<Competencia> getAll() {
        return competenciaService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener competencia por ID", description = "Devuelve una competencia por su ID")
    public ResponseEntity<Competencia> getById(@PathVariable Long id) {
        Optional<Competencia> competencia = competenciaService.findById(id);
        return competencia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva competencia", description = "Crea una nueva competencia")
    public Competencia create(@Valid @RequestBody CompetenciaRequestDto request) {
        return competenciaService.save(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una competencia", description = "Actualiza una competencia existente por su ID")
    public ResponseEntity<Competencia> update(@Valid @PathVariable Long id, @RequestBody CompetenciaRequestDto request) {
        return competenciaService.update(id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una competencia", description = "Elimina una competencia existente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (competenciaService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}