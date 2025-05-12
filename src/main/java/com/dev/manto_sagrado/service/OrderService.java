package com.dev.manto_sagrado.service;

import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.order.Enum.Status;
import com.dev.manto_sagrado.domain.order.dto.OrderRequestDTO;
import com.dev.manto_sagrado.domain.order.dto.OrderResponseDTO;
import com.dev.manto_sagrado.domain.order.dto.OrderStatusDTO;
import com.dev.manto_sagrado.domain.order.entity.Order;
import com.dev.manto_sagrado.domain.orderItems.dto.OrderItemsRequestDTO;
import com.dev.manto_sagrado.domain.orderItems.dto.OrderItemsResponseDTO;
import com.dev.manto_sagrado.domain.orderItems.entity.OrderItems;
import com.dev.manto_sagrado.repository.ClientRepository;
import com.dev.manto_sagrado.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    public List<OrderResponseDTO> listAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream().map(OrderResponseDTO::fromOrder).toList();
    }

    public List<OrderResponseDTO> listAllByClient(ClientRequestDTO client) {
        return repository.findAllByClient(ClientRequestDTO.newUser(client))
                .stream()
                .map(OrderResponseDTO::fromOrder)
                .collect(Collectors.toList());
    }

    public Optional<OrderResponseDTO> save(OrderRequestDTO order) {
        return Optional.of(OrderResponseDTO.fromOrder(repository.save(OrderRequestDTO.newOrder(order))));
    }

    @Transactional
    public Optional<OrderResponseDTO> updateOrderStatus(long orderId, Status status) {
        if (!repository.existsById(orderId)) return Optional.empty();

        Order order = repository.findById(orderId).get();
        order.setStatus(status);

        return Optional.of(OrderResponseDTO.fromOrder(repository.save(order)));
    }

    public Optional<OrderResponseDTO> findByClientAndOrder(long clientId, long orderId) {
        if (!repository.existsById(orderId) && !clientRepository.existsById(clientId)) return Optional.empty();

        Client client = clientRepository.findById(clientId).get();
        return Optional.of(OrderResponseDTO.fromOrder(repository.findByIdAndClient(orderId, client)));
    }
}
