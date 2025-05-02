package com.dev.manto_sagrado.repository;

import com.dev.manto_sagrado.domain.order.entity.Order;
import com.dev.manto_sagrado.domain.orderItems.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findAllByOrder(Order order);
}
