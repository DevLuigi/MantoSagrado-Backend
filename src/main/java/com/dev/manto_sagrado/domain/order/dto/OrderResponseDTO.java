package com.dev.manto_sagrado.domain.order.dto;

import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.order.Enum.Payment;
import com.dev.manto_sagrado.domain.order.Enum.Status;
import com.dev.manto_sagrado.domain.order.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDTO {
    private long id;
    private BigDecimal totalPrice;
    private BigDecimal shippingCost;
    private Address address;
    private Client client;
    private Payment payment;
    private Status status;
    private LocalDateTime createdAt;

    public static OrderResponseDTO fromOrder(Order order){
        return OrderResponseDTO.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .shippingCost(order.getShippingCost())
                .address(order.getAddress())
                .client(order.getClient())
                .payment(order.getPayment())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
