package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.RolRequestDto;
import org.fenc.puntodeventa.model.Competencia;
import org.fenc.puntodeventa.model.Rol;
import org.fenc.puntodeventa.repository.CompetenciaRepository;
import org.fenc.puntodeventa.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;
    private final CompetenciaRepository competenciaRepository;

    public List<Rol> findAll() {

        return rolRepository.findAll();
    }

    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

    public Rol save(RolRequestDto request) {
        List<Competencia> competencias = competenciaRepository.findAllById(request.getCompetenciasId());
        if (competencias.isEmpty()) {
            throw new RuntimeException("Competencias no encontradas");
        }
        Rol rol = Rol.builder()
                .rol(request.getRol())
                .estado(true)
                .competencias(competencias)
                .build();
        return rolRepository.save(rol);
    }

    public Optional<Rol> update(Long id, RolRequestDto request) {
        List<Competencia> competencias = competenciaRepository.findAllById(request.getCompetenciasId());
        if (competencias.isEmpty()) {
            throw new RuntimeException("Competencias no encontradas");
        }
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setRol(request.getRol());
        rol.setEstado(request.getEstado());
        rol.setCompetencias(competencias);
        return Optional.of(rolRepository.save(rol));
    }

    public boolean delete(Long id) {

        return rolRepository.findById(id)
                .map(rol -> {
                    rolRepository.delete(rol);
                    return true;
                }).orElse(false);
    }

public Optional<Rol> addCompetencia(Long idRol, List<Long> competenciasId) {
    Optional<Rol> rolOptional = rolRepository.findById(idRol);
    if (rolOptional.isPresent()) {
        Rol rol = rolOptional.get();
        List<Competencia> competencias = competenciaRepository.findAllById(competenciasId);
        if (!competencias.isEmpty()) {
            competencias.forEach(competencia -> {
                if (!rol.getCompetencias().contains(competencia)) {
                    rol.getCompetencias().add(competencia);
                }
            });
            return Optional.of(rolRepository.save(rol));
        }
    }
    return Optional.empty();
}

    public boolean removeCompetencia(Long idRol, Long idCompetencia) {
        Optional<Rol> rolOptional = rolRepository.findById(idRol);
        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();
            Competencia comp = competenciaRepository.findById(idCompetencia).orElse(null);
            if (comp != null) {
                rol.getCompetencias().remove(comp);
                rolRepository.save(rol);
                return true;
            }
        }
        return false;
    }
}
