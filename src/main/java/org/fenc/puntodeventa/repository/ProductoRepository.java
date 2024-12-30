package org.fenc.puntodeventa.repository;

import org.fenc.puntodeventa.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
