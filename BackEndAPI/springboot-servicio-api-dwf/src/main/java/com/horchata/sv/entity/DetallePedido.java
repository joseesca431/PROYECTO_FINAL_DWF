package com.horchata.sv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "descuento_unitario", precision = 10, scale = 2)
    private BigDecimal descuentoUnitario = BigDecimal.ZERO;

    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @PrePersist
    @PreUpdate
    private void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            if (descuentoUnitario != null) {
                total = total.subtract(descuentoUnitario.multiply(BigDecimal.valueOf(cantidad)));
            }
            this.subtotal = total;
        }
    }
}
