package com.dev.manto_sagrado.controller;

import com.dev.manto_sagrado.domain.client.dto.ClientRequestDTO;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.order.dto.OrderRequestDTO;
import com.dev.manto_sagrado.domain.order.dto.OrderResponseDTO;
import com.dev.manto_sagrado.domain.orderItems.dto.OrderItemsRequestDTO;
import com.dev.manto_sagrado.domain.orderItems.dto.OrderItemsResponseDTO;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminRequestDTO;
import com.dev.manto_sagrado.domain.productAdmin.dto.ProductAdminResponseDTO;
import com.dev.manto_sagrado.service.OrderItemsService;
import com.dev.manto_sagrado.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemsService orderItemsService;

    @PostMapping("/list-all-by-client")
    public ResponseEntity<List<OrderResponseDTO>> listAllByClient(@RequestBody ClientRequestDTO data) {
        return ResponseEntity.ok().body(orderService.listAllByClient(data));
    }

    @PostMapping("/{orderId}/client/{clientId}")
    public ResponseEntity<Optional<OrderResponseDTO>> findByClientAndOrder(@PathVariable("clientId") long clientId, @PathVariable("orderId") long orderId){
        return ResponseEntity.ok().body(orderService.findByClientAndOrder(clientId, orderId));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@Valid @RequestBody OrderRequestDTO data) {
        Optional<OrderResponseDTO> inserted = orderService.save(data);
        return inserted.isPresent() ? ResponseEntity.ok().body(inserted.get()) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/items/list-all-by-order")
    public ResponseEntity<List<OrderItemsResponseDTO>> listAllByOrder(@RequestBody OrderRequestDTO data) {
        return ResponseEntity.ok().body(orderItemsService.listAllByOrder(data));
    }

    @PostMapping("/items")
    public ResponseEntity<OrderItemsResponseDTO> saveOrderItems(@Valid @RequestBody OrderItemsRequestDTO data) {
        Optional<OrderItemsResponseDTO> inserted = orderItemsService.save(data);
        return inserted.isPresent() ? ResponseEntity.ok().body(inserted.get()) : ResponseEntity.badRequest().build();
    }

}
