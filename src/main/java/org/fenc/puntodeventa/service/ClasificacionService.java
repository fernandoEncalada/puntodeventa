package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.Clasificacion;
import org.fenc.puntodeventa.repository.ClasificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClasificacionService {

    private final ClasificacionRepository clasificacionRepository;

    public List<Clasificacion> findAll() {
        return clasificacionRepository.findAll();
    }

    public Optional<Clasificacion> findById(Long id) {

        return clasificacionRepository.findById(id);
    }

    public Clasificacion save(Clasificacion clasificacion) {

        return clasificacionRepository.save(clasificacion);
    }

    public Optional<Clasificacion> update(Long id, Clasificacion clasificacion) {

        return clasificacionRepository.findById(id)
                .map(record -> {
                    record.setGrupo(clasificacion.getGrupo());
                    Clasificacion updated = clasificacionRepository.save(record);
                    return Optional.of(updated);
                })
                .orElseGet(() -> Optional.empty());
    }

    public boolean delete(Long id) {

        return clasificacionRepository.findById(id)
                .map(record -> {
                    clasificacionRepository.deleteById(id);
                    return true;
                })
                .orElseGet(() -> false);
    }
}