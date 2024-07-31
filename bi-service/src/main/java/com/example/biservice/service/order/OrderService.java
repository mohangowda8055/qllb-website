package com.example.biservice.service.order;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.order.OrderDto;
import com.example.biservice.payload.order.OrderResponseDto;

public interface OrderService {

    OrderResponseDto createOrder(OrderDto orderDto, Long userId);

    OrderResponseDto getOrder(Long orderId);

    PageResponse<OrderResponseDto> findAllOrdersByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy);

    PageResponse<OrderResponseDto> findAllOrders(Integer pageNumber, Integer pageSize, String sortBy);

    OrderDto updateOrder(OrderDto orderDto,Long orderId);

    void updateOrderPlaced(Long orderId);

    void updateOrderFailed(Long orderId);

    void updateOrderShipped(Long orderId);

    void updateOrderOutForDelivery(Long orderId);

    void updateOrderDelivered(Long orderId);

}
