package com.horchata.sv.service.Impl;
import com.horchata.sv.entity.Producto;
import com.horchata.sv.repository.ProductoRepository;
import com.horchata.sv.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }

    @Override
    public List<Producto> buscarProductos(String busqueda) {
        return productoRepository.buscarProductos(busqueda);
    }

    @Override
    public List<Producto> listarPorMarca(Long marcaId) {
        return productoRepository.findByMarcaIdAndActivoTrue(marcaId);
    }

    @Override
    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId);
    }

    @Override
    public List<Producto> listarConStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setActivo(false);
            productoRepository.save(p);
        }
    }

    @Override
    public Producto actualizarStock(Long id, Integer nuevoStock) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setStockActual(nuevoStock);
            return productoRepository.save(producto);
        }
        throw new RuntimeException("Producto no encontrado");
    }
}
