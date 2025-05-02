package com.dev.manto_sagrado.domain.orderItems.entity;

import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.order.entity.Order;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "orderItems")
@Entity(name = "OrderItems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "O produto deve ser preenchido")
    @ManyToOne @JoinColumn(name = "fk_product", nullable = false)
    private ProductAdmin product;

    @NotNull(message = "a quantidade deve ser preenchida")
    private int quantity;

    @NotNull(message = "O preço deve ser preenchido")
    private BigDecimal unitPrice;

    @NotNull(message = "O pedido deve ser preenchido")
    @ManyToOne @JoinColumn(name = "fk_order", nullable = false)
    private Order order;

}
