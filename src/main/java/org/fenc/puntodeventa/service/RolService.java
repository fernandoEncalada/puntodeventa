package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.Competencia;
import org.fenc.puntodeventa.model.Rol;
import org.fenc.puntodeventa.repository.CompetenciaRepository;
import org.fenc.puntodeventa.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Rol save(Rol rol) {

        return rolRepository.save(rol);
    }

    public Optional<Rol> update(Long id, Rol rol) {
        return rolRepository.findById(id)
                .map(rol1 -> {
                    rol1.setRol(rol.getRol());
                    rol1.setEstado(true);
                    return rolRepository.save(rol1);
                });
    }

    public boolean delete(Long id) {

        return rolRepository.findById(id)
                .map(rol -> {
                    rolRepository.delete(rol);
                    return true;
                }).orElse(false);
    }

    public Optional<Rol> addCompetencia(Long idRol, Competencia competencia) {
        Optional<Rol> rolOptional = rolRepository.findById(idRol);
        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();
            Competencia comp = competenciaRepository.findById(competencia.getIdCompetencia())
                    .orElseThrow(() -> new RuntimeException("Competencia no encontrada"));
            if (comp != null) {
                rol.getCompetencias().add(comp);
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
