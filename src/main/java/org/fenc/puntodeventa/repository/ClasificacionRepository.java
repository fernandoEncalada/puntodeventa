package org.fenc.puntodeventa.repository;

import org.fenc.puntodeventa.model.Clasificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClasificacionRepository extends JpaRepository<Clasificacion, Long> {
//    Optional<Clasificacion> findByNombre(String nombre);
}
