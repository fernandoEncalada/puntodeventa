package org.fenc.puntodeventa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.UsuarioRequestDto;
import org.fenc.puntodeventa.model.Persona;
import org.fenc.puntodeventa.model.Usuario;
import org.fenc.puntodeventa.repository.PersonaRepository;
import org.fenc.puntodeventa.repository.RolRepository;
import org.fenc.puntodeventa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario save(UsuarioRequestDto request) {
        var persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        var usuario = Usuario.builder()
                .persona(persona)
                .user(request.getUser())
                .password(request.getPassword())
                .build();

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Optional<Usuario> update(Long id, UsuarioRequestDto requestDto) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setUser(requestDto.getUser());
        usuario.setPassword(requestDto.getPassword());
        return Optional.of(usuarioRepository.save(usuario));
    }

    @Transactional
    public boolean delete(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Usuario> addRoles(Long id, List<Long> rolesId) {

        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var roles = rolRepository.findAllById(rolesId);
        if (roles.isEmpty()) {
            throw new RuntimeException("Roles no encontrados");
        }
        usuario.setRoles(roles);
        return Optional.of(usuarioRepository.save(usuario));
    }
}