package com.horchata.sv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", unique = true, nullable = false)
    private String numeroPedido;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column(name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;

    @Column(name = "fecha_entrega_real")
    private LocalDateTime fechaEntregaReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "descuento", precision = 10, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(name = "impuestos", precision = 10, scale = 2)
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    private String observaciones;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @Column(name = "telefono_contacto")
    private String telefonoContacto;

    @Column(name = "prioridad")
    @Enumerated(EnumType.STRING)
    private PrioridadPedido prioridad = PrioridadPedido.NORMAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Cliente que realizó el pedido

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovimientoInventario> movimientos;

    public enum EstadoPedido {
        PENDIENTE, PROCESANDO, EN_PREPARACION, LISTO, ENVIADO, ENTREGADO, CANCELADO
    }

    public enum PrioridadPedido {
        BAJA, NORMAL, ALTA, URGENTE
    }
    // Método para calcular totales
    public void calcularTotales() {
        if (detalles != null && !detalles.isEmpty()) {
            subtotal = detalles.stream()
                    .map(DetallePedido::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            total = subtotal.add(impuestos).subtract(descuento);
        }
    }
}
