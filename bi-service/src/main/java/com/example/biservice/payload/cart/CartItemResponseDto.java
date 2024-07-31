package com.example.biservice.payload.cart;

import com.example.biservice.payload.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {

    private CartItemIdDto id;

    private int quantity;

    private boolean isRebate;

    private ProductResponseDto product;

    @JsonProperty("isRebate")
    public boolean isRebate() {
        return isRebate;
    }

    public void setRebate(boolean rebate) {
        isRebate = rebate;
    }
}
