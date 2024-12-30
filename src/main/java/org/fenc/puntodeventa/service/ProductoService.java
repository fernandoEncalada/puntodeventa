package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.model.Producto;
import org.fenc.puntodeventa.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> update(Long id, Producto producto) {
        if (productoRepository.existsById(id)) {
            producto.setIdProducto(id);
            return Optional.of(productoRepository.save(producto));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}