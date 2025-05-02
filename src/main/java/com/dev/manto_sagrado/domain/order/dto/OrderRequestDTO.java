package com.dev.manto_sagrado.domain.order.dto;

import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.order.Enum.Payment;
import com.dev.manto_sagrado.domain.order.Enum.Status;
import com.dev.manto_sagrado.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private long id;
    private BigDecimal totalPrice;
    private BigDecimal shippingCost;
    private Address address;
    private Client client;
    private Payment payment;
    private Status status;

    public static Order newOrder(OrderRequestDTO order){
        return Order.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .shippingCost(order.getShippingCost())
                .address(order.getAddress())
                .client(order.getClient())
                .payment(order.getPayment())
                .status(order.getStatus())
                .build();
    }
}
