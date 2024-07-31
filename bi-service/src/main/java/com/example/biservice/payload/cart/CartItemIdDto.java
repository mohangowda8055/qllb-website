package com.example.biservice.payload.cart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemIdDto {

    private Long cartId;

    private Integer productId;
}
