package com.horchata.sv.service.Impl;
import com.horchata.sv.entity.DetallePedido;
import com.horchata.sv.entity.Pedido;
import com.horchata.sv.entity.Producto;
import com.horchata.sv.repository.PedidoRepository;
import com.horchata.sv.repository.ProductoRepository;
import com.horchata.sv.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Pedido crearPedido(Pedido pedido) {
        // Generar número de pedido único
        String numeroPedido = generarNumeroPedido();
        pedido.setNumeroPedido(numeroPedido);

        // Procesar detalles y actualizar stock
        if (pedido.getDetalles() != null) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);

                // Verificar y actualizar stock
                Producto producto = detalle.getProducto();
                if (producto.getStockActual() < detalle.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
                }

                producto.setStockActual(producto.getStockActual() - detalle.getCantidad());
                productoRepository.save(producto);
            }
        }

        // Calcular totales
        pedido.calcularTotales();

        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<Pedido> listarPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaPedidoDesc(usuarioId);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Optional<Pedido> buscarPorNumeroPedido(String numeroPedido) {
        return pedidoRepository.findByNumeroPedido(numeroPedido);
    }

    @Override
    public List<Pedido> listarPorEstado(Pedido.EstadoPedido estado) {
        return pedidoRepository.findByEstadoOrderByFechaPedidoDesc(estado);
    }

    @Override
    public Pedido actualizarEstadoPedido(Long id, Pedido.EstadoPedido nuevoEstado) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado(nuevoEstado);

            if (nuevoEstado == Pedido.EstadoPedido.ENTREGADO) {
                pedido.setFechaEntregaReal(LocalDateTime.now());
            }

            return pedidoRepository.save(pedido);
        }
        throw new RuntimeException("Pedido no encontrado");
    }

    @Override
    public List<Pedido> listarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);
    }

    private String generarNumeroPedido() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = pedidoRepository.count() + 1;
        return "PED-" + fecha + "-" + String.format("%04d", count);
    }
}
