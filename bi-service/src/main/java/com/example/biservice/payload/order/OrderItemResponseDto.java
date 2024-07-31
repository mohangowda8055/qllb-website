package com.example.biservice.payload.order;

import com.example.biservice.payload.ProductResponseDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

    private OrderItemIdDto id;

    private int quantity;

    private boolean isRebate;

    private ProductResponseDto product;
}
