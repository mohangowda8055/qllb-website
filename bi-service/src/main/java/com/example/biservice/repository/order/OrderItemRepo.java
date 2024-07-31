package com.example.biservice.repository.order;

import com.example.biservice.entity.order.OrderItem;
import com.example.biservice.entity.order.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, OrderItemId> {

    List<OrderItem> findAllByIdOrderId(Long orderId);

    Optional<OrderItem> findByIdOrderIdAndIdProductId(Long orderId, Integer productId);
}
