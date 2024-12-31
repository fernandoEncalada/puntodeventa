package org.fenc.puntodeventa.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fenc.puntodeventa.dto.FacturaRequestDto;
import org.fenc.puntodeventa.dto.ItemFacturaRequestDto;
import org.fenc.puntodeventa.model.Factura;
import org.fenc.puntodeventa.model.ItemFactura;
import org.fenc.puntodeventa.model.Persona;
import org.fenc.puntodeventa.model.Producto;
import org.fenc.puntodeventa.model.TipoPago;
import org.fenc.puntodeventa.repository.FacturaRepository;
import org.fenc.puntodeventa.repository.ItemFacturaRepository;
import org.fenc.puntodeventa.repository.PersonaRepository;
import org.fenc.puntodeventa.repository.ProductoRepository;
import org.fenc.puntodeventa.repository.TipoPagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final ItemFacturaRepository itemFacturaRepository;
    private final ProductoRepository productoRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final PersonaRepository personaRepository;

    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> findById(Long id) {
        return facturaRepository.findById(id);
    }

    @Transactional
    public Factura createFactura(FacturaRequestDto facturaRequestDto) {
        // Validar productos y calcular totales
        double total = 0.0;
        for (ItemFacturaRequestDto itemRequest : facturaRequestDto.getItems()) {
            Producto producto = productoRepository.findById(itemRequest.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            if (producto.getStock() < itemRequest.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getIdProducto());
            }

            // Calcular subtotal
            double subtotal = itemRequest.getCantidad() * producto.getPrecioUnitario();
            total += subtotal;

            // Actualizar stock
            producto.setStock(producto.getStock() - itemRequest.getCantidad());
            productoRepository.save(producto);
        }

        TipoPago tipoPago = tipoPagoRepository.findById(facturaRequestDto.getIdTipoPago())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de pago no encontrado"));
        Persona persona = personaRepository.findById(facturaRequestDto.getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
        // Crear factura
        Factura factura = new Factura();
        factura.setRuc(facturaRequestDto.getRuc());
        factura.setFecha(java.sql.Date.valueOf(facturaRequestDto.getFecha()));
        factura.setTipoPago(tipoPago);
        factura.setPersona(persona);
        factura.setDescuento(facturaRequestDto.getDescuento());
        factura.setTotal(total - facturaRequestDto.getDescuento());
        factura = facturaRepository.save(factura);


        // Crear items de factura
        for (ItemFacturaRequestDto itemRequest : facturaRequestDto.getItems()) {
            Producto producto = productoRepository.findById(itemRequest.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            ItemFactura itemFactura = new ItemFactura();
            itemFactura.setFactura(factura);
            itemFactura.setProducto(producto);
            itemFactura.setCantidad(itemRequest.getCantidad());
            itemFactura.setPrecio(itemRequest.getCantidad() * producto.getPrecioUnitario());
            itemFactura.setSubtotal(itemFactura.getPrecio());
            itemFacturaRepository.save(itemFactura);
        }

        return factura;
    }

    @Transactional
    public boolean delete(Long id) {
        return facturaRepository.findById(id)
                .map(factura -> {
                    // Eliminar items de la factura
                    List<ItemFactura> items = itemFacturaRepository.findByFacturaId(id);
                    itemFacturaRepository.deleteAll(items);

                    // Eliminar la factura
                    facturaRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}