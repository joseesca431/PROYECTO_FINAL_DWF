package com.horchata.sv.service;

import com.horchata.sv.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto crearProducto(Producto producto);

    List<Producto> listarProductos();

    Optional<Producto> buscarPorId(Long id);

    Optional<Producto> buscarPorCodigo(String codigo);

    List<Producto> buscarProductos(String busqueda);

    List<Producto> listarPorMarca(Long marcaId);

    List<Producto> listarPorCategoria(Long categoriaId);

    List<Producto> listarConStockBajo();

    Producto actualizarProducto(Producto producto);

    void eliminarProducto(Long id);

    Producto actualizarStock(Long id, Integer nuevoStock);
}
