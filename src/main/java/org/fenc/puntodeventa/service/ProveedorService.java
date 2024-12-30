package org.fenc.puntodeventa.service;

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

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Optional<Proveedor> update(Long id, Proveedor proveedor) {
        if (proveedorRepository.existsById(id)) {
            proveedor.setIdProveedor(id);
            return Optional.of(proveedorRepository.save(proveedor));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
