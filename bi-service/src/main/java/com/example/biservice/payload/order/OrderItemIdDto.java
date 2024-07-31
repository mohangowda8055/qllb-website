package com.example.biservice.payload.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemIdDto {

    private Long orderId;

    private Integer productId;
}
