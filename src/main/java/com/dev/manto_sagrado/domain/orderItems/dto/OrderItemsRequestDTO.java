package com.dev.manto_sagrado.domain.orderItems.dto;

import com.dev.manto_sagrado.domain.order.entity.Order;
import com.dev.manto_sagrado.domain.orderItems.entity.OrderItems;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsRequestDTO {
    private long id;
    private ProductAdmin product;
    private int quantity;
    private BigDecimal unitPrice;
    private Order order;

    public static OrderItems newOrderItems(OrderItemsRequestDTO orderItems) {
        return OrderItems.builder()
                .id(orderItems.getId())
                .product(orderItems.getProduct())
                .quantity(orderItems.getQuantity())
                .unitPrice(orderItems.getUnitPrice())
                .order(orderItems.getOrder())
                .build();
    }
}
