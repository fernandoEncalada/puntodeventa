package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.Persona;
import org.fenc.puntodeventa.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository personaRepository;

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Optional<Persona> findById(Long id) {
        return personaRepository.findById(id);
    }

    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    public Optional<Persona> update(Long id, Persona persona) {
        if (personaRepository.existsById(id)) {
            persona.setIdPersona(id);
            return Optional.of(personaRepository.save(persona));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}