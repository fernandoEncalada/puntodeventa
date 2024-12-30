package org.fenc.puntodeventa.repository;

import org.fenc.puntodeventa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
