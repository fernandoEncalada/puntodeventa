package org.fenc.puntodeventa.repository;

import org.fenc.puntodeventa.model.ItemFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemFacturaRepository extends JpaRepository<ItemFactura, Long> {
    @Query("SELECT i FROM ItemFactura i WHERE i.factura.idFactura = :idFactura")
    List<ItemFactura> findByFacturaId(@Param("idFactura") Long idFactura);
}
