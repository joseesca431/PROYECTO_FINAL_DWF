package com.horchata.sv.service;

import com.horchata.sv.entity.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido crearPedido(Pedido pedido);

    List<Pedido> listarTodosPedidos();

    List<Pedido> listarPedidosPorUsuario(Long usuarioId);

    Optional<Pedido> buscarPorId(Long id);

    Optional<Pedido> buscarPorNumeroPedido(String numeroPedido);

    List<Pedido> listarPorEstado(Pedido.EstadoPedido estado);

    Pedido actualizarEstadoPedido(Long id, Pedido.EstadoPedido nuevoEstado);

    List<Pedido> listarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
