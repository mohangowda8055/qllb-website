package com.example.biservice.payload.order;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private OrderItemIdDto id;

    private int quantity;

    private boolean isRebate;
}
