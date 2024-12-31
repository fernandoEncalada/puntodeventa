package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.CompetenciaRequestDto;
import org.fenc.puntodeventa.model.Competencia;
import org.fenc.puntodeventa.repository.CompetenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompetenciaService {
    private final CompetenciaRepository competenciaRepository;

    public List<Competencia> findAll() {


        return competenciaRepository.findAll();
    }

    public Optional<Competencia> findById(Long id) {
        return competenciaRepository.findById(id);
    }

    public Competencia save(CompetenciaRequestDto request) {
        Competencia competencia = Competencia.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();
        return competenciaRepository.save(competencia);
    }

    public Optional<Competencia> update(Long id, CompetenciaRequestDto request) {
        return competenciaRepository.findById(id)
                .map(record -> {
                    record.setNombre(request.getNombre());
                    record.setDescripcion(request.getDescripcion());
                    return competenciaRepository.save(record);
                });
    }

    public boolean delete(Long id) {
        return competenciaRepository.findById(id)
                .map(record -> {
                    competenciaRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
