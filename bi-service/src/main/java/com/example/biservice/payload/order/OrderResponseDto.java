package com.example.biservice.payload.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;

    private float total;

    private float deliveryCost;

    private float grandTotal;

    private String paymentMethod;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private String orderStatus;

    private ShippingAddressDto shippingAddress;

    private List<OrderItemResponseDto> orderItems;

    private PaymentDetailDto paymentDetail;
}
