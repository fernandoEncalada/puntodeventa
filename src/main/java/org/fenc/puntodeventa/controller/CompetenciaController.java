package org.fenc.puntodeventa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.Clasificacion;
import org.fenc.puntodeventa.model.Competencia;
import org.fenc.puntodeventa.service.ClasificacionService;
import org.fenc.puntodeventa.service.CompetenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competencia")
@RequiredArgsConstructor
@Tag(name = "Competencia", description = "API de Competencia")
public class CompetenciaController {
    private final CompetenciaService competenciaService;

    @GetMapping
    public List<Competencia> getAll() {
        return competenciaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competencia> getById(@PathVariable Long id) {
        Optional<Competencia> competencia = competenciaService.findById(id);
        return competencia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Competencia create(@RequestBody Competencia competencia) {
        return competenciaService.save(competencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competencia> update(@PathVariable Long id, @RequestBody Competencia competencia) {
        return competenciaService.update(id, competencia)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (competenciaService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}