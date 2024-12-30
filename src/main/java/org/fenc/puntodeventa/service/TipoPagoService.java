package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.TipoPago;
import org.fenc.puntodeventa.repository.TipoPagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoPagoService {
    private final TipoPagoRepository tipoPagoRepository;

    public List<TipoPago> findAll() {
        return tipoPagoRepository.findAll();
    }

    public Optional<TipoPago> findById(Long id) {
        return tipoPagoRepository.findById(id);
    }

    public TipoPago save(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }

    public Optional<TipoPago> update(Long id, TipoPago tipoPago) {
        if (tipoPagoRepository.existsById(id)) {
            tipoPago.setIdTipoPago(id);
            return Optional.of(tipoPagoRepository.save(tipoPago));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        if (tipoPagoRepository.existsById(id)) {
            tipoPagoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}