package com.example.biservice.payload.cart;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long cartId;

    private String pincode;

    private float total;

    private float deliveryCost;

    private List<CartItemDto> cartItemDtoList;
}
