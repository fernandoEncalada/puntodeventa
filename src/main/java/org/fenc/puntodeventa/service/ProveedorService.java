package org.fenc.puntodeventa.service;

import org.fenc.puntodeventa.dto.ProveedorRequestDto;
import org.fenc.puntodeventa.model.Proveedor;
import org.fenc.puntodeventa.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor save(ProveedorRequestDto requestDto) {
        Proveedor proveedor = Proveedor.builder()
                .ruc(requestDto.getRuc())
                .telefono(requestDto.getTelefono())
                .pais(requestDto.getPais())
                .correo(requestDto.getCorreo())
                .moneda(requestDto.getMoneda())
                .build();
        return proveedorRepository.save(proveedor);
    }

    public Optional<Proveedor> update(Long id, ProveedorRequestDto requestDto) {
        return proveedorRepository.findById(id)
                .map(record -> {
                    record.setRuc(requestDto.getRuc());
                    record.setTelefono(requestDto.getTelefono());
                    record.setPais(requestDto.getPais());
                    record.setCorreo(requestDto.getCorreo());
                    record.setMoneda(requestDto.getMoneda());
                    Proveedor updated = proveedorRepository.save(record);
                    return Optional.of(updated);
                })
                .orElseGet(Optional::empty);
    }

    public boolean delete(Long id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
