package org.fenc.puntodeventa.controller;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.TipoPago;
import org.fenc.puntodeventa.service.TipoPagoService;
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
@RequestMapping("/tipo-pago")
@RequiredArgsConstructor
public class TipoPagoController {
    private final TipoPagoService tipoPagoService;

    @GetMapping
    public List<TipoPago> getAll() {
        return tipoPagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPago> getById(@PathVariable Long id) {
        Optional<TipoPago> tipoPago = tipoPagoService.findById(id);
        return tipoPago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoPago create(@RequestBody TipoPago tipoPago) {
        return tipoPagoService.save(tipoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoPago> update(@PathVariable Long id, @RequestBody TipoPago tipoPago) {
        return tipoPagoService.update(id, tipoPago)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (tipoPagoService.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}