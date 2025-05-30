package com.horchata.sv.repository;

import com.horchata.sv.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigo(String codigo);
    List<Producto> findByActivoTrue();
    List<Producto> findByMarcaIdAndActivoTrue(Long marcaId);
    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);

    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();

    @Query("SELECT p FROM Producto p WHERE " +
            "(LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.codigo) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.numeroParte) LIKE LOWER(CONCAT('%', :busqueda, '%'))) " +
            "AND p.activo = true")
    List<Producto> buscarProductos(@Param("busqueda") String busqueda);
}
