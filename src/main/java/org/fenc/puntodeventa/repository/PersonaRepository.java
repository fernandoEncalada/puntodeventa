package org.fenc.puntodeventa.repository;

import org.fenc.puntodeventa.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
