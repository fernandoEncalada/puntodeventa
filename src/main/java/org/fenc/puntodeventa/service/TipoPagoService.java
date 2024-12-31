package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.TipoPagoRequestDto;
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

    public TipoPago save(TipoPagoRequestDto requestDto) {
        TipoPago tipoPago = TipoPago.builder()
                .descripcion(requestDto.getDescripcion())
                .tipo(requestDto.getTipo())
                .build();
        return tipoPagoRepository.save(tipoPago);
    }

    public Optional<TipoPago> update(Long id, TipoPagoRequestDto requestDto) {
        TipoPago tipoPago = tipoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado"));
        tipoPago.setDescripcion(requestDto.getDescripcion());
        tipoPago.setTipo(requestDto.getTipo());
        return Optional.of(tipoPagoRepository.save(tipoPago));
    }

    public boolean delete(Long id) {
        if (tipoPagoRepository.existsById(id)) {
            tipoPagoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}