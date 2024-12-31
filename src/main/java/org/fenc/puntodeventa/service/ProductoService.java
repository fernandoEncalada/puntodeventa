package org.fenc.puntodeventa.service;

import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.ProductoRequestDto;
import org.fenc.puntodeventa.model.Producto;
import org.fenc.puntodeventa.repository.ClasificacionRepository;
import org.fenc.puntodeventa.repository.ProductoRepository;
import org.fenc.puntodeventa.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ClasificacionRepository clasificacionRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto save(ProductoRequestDto requestDto) {
        var proveedor = proveedorRepository.findById(requestDto.getIdProveedor()).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        var clasificacion = clasificacionRepository.findById(requestDto.getIdClasificacion()).orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));
        Producto producto = Producto.builder()
                .stock(requestDto.getStock())
                .precioUnitario(requestDto.getPrecioUnitario())
                .unidad(requestDto.getUnidad())
                .iva(requestDto.getIva())
                .proveedor(proveedor)
                .clasificacion(clasificacion)
                .build();
        return productoRepository.save(producto);
    }

    public Optional<Producto> update(Long id, ProductoRequestDto requestDto) {
        return productoRepository.findById(id)
                .map(record -> {
                    var proveedor = proveedorRepository.findById(requestDto.getIdProveedor()).orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
                    var clasificacion = clasificacionRepository.findById(requestDto.getIdClasificacion()).orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));
                    record.setStock(requestDto.getStock());
                    record.setPrecioUnitario(requestDto.getPrecioUnitario());
                    record.setUnidad(requestDto.getUnidad());
                    record.setIva(requestDto.getIva());
                    record.setProveedor(proveedor);
                    record.setClasificacion(clasificacion);
                    Producto updated = productoRepository.save(record);
                    return Optional.of(updated);
                })
                .orElseGet(() -> Optional.empty());
    }

    public boolean delete(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}