package org.fenc.puntodeventa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.UsuarioRequestDto;
import org.fenc.puntodeventa.model.Persona;
import org.fenc.puntodeventa.model.Usuario;
import org.fenc.puntodeventa.repository.PersonaRepository;
import org.fenc.puntodeventa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario save(UsuarioRequestDto request) {
        var persona = Persona.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .celular(request.getCelular())
                .correo(request.getCorreo())
                .build();
        persona = personaRepository.save(persona);
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
        var persona = usuario.getPersona();
        persona.setNombre(requestDto.getNombre());
        persona.setApellido(requestDto.getApellido());
        persona.setDni(requestDto.getDni());
        persona.setCelular(requestDto.getCelular());
        persona.setCorreo(requestDto.getCorreo());
        personaRepository.save(persona);
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
}