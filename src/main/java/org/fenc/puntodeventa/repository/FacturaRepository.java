package org.fenc.puntodeventa.repository;

import org.fenc.puntodeventa.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
