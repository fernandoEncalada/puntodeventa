package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.PersonaRequestDto;
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

    public Persona save(PersonaRequestDto requestDto) {
        Persona persona = Persona.builder()
                .nombre(requestDto.getNombre())
                .apellido(requestDto.getApellido())
                .dni(requestDto.getDni())
                .celular(requestDto.getCelular())
                .correo(requestDto.getCorreo())
                .build();
        return personaRepository.save(persona);
    }

    public Optional<Persona> update(Long id, PersonaRequestDto requestDto) {
        return personaRepository.findById(id)
                .map(record -> {
                    record.setNombre(requestDto.getNombre());
                    record.setApellido(requestDto.getApellido());
                    record.setDni(requestDto.getDni());
                    record.setCelular(requestDto.getCelular());
                    record.setCorreo(requestDto.getCorreo());
                    Persona updated = personaRepository.save(record);
                    return Optional.of(updated);
                })
                .orElseGet(Optional::empty);
    }

    public boolean delete(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}