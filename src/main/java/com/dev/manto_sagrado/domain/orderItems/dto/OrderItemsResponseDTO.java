package com.dev.manto_sagrado.domain.orderItems.dto;

import com.dev.manto_sagrado.domain.order.entity.Order;
import com.dev.manto_sagrado.domain.orderItems.entity.OrderItems;
import com.dev.manto_sagrado.domain.productAdmin.entity.ProductAdmin;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemsResponseDTO {
    private long id;
    private ProductAdmin product;
    private int quantity;
    private BigDecimal unitPrice;
    private Order order;

    public static OrderItemsResponseDTO fromOrderItems(OrderItems orderItems){
        return OrderItemsResponseDTO.builder()
                .id(orderItems.getId())
                .product(orderItems.getProduct())
                .quantity(orderItems.getQuantity())
                .unitPrice(orderItems.getUnitPrice())
                .order(orderItems.getOrder())
                .build();
    }
}
