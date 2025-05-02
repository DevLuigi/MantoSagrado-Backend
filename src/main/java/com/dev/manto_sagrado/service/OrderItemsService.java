package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.order.dto.OrderRequestDTO;
import com.dev.manto_sagrado.domain.order.dto.OrderResponseDTO;
import com.dev.manto_sagrado.domain.order.entity.Order;
import com.dev.manto_sagrado.domain.orderItems.dto.OrderItemsRequestDTO;
import com.dev.manto_sagrado.domain.orderItems.dto.OrderItemsResponseDTO;
import com.dev.manto_sagrado.domain.orderItems.entity.OrderItems;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminResponseDTO;
import com.dev.manto_sagrado.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {

    @Autowired
    private OrderItemsRepository repository;

    public List<OrderItemsResponseDTO> listAllByOrder(OrderRequestDTO order) {
        return repository.findAllByOrder(OrderRequestDTO.newOrder(order))
                .stream()
                .map(OrderItemsResponseDTO::fromOrderItems)
                .collect(Collectors.toList());
    }

    public Optional<OrderItemsResponseDTO> save(OrderItemsRequestDTO orderItems){
        return Optional.of(OrderItemsResponseDTO.fromOrderItems(repository.save(OrderItemsRequestDTO.newOrderItems(orderItems))));
    }


}
