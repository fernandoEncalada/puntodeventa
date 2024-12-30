package org.fenc.puntodeventa.controller;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.Clasificacion;
import org.fenc.puntodeventa.model.Competencia;
import org.fenc.puntodeventa.model.Rol;
import org.fenc.puntodeventa.service.ClasificacionService;
import org.fenc.puntodeventa.service.RolService;
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
@RequestMapping("/clasificacion")
@RequiredArgsConstructor
public class ClasificacionController {
    private final ClasificacionService clasificacionService;

    @GetMapping
    public List<Clasificacion> getAll() {
        return clasificacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clasificacion> getById(@PathVariable Long id) {
        Optional<Clasificacion> clasificacion = clasificacionService.findById(id);
        return clasificacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Clasificacion create(@RequestBody Clasificacion clasificacion) {
        return clasificacionService.save(clasificacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clasificacion> update(@PathVariable Long id, @RequestBody Clasificacion clasificacion) {
        return clasificacionService.update(id, clasificacion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (clasificacionService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}