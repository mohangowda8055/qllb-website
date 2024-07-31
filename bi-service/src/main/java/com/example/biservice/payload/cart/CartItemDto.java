package com.example.biservice.payload.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private CartItemIdDto id;

    private int quantity;

    private boolean isRebate;



    @JsonProperty("isRebate")
    public boolean isRebate() {
        return isRebate;
    }

    public void setRebate(boolean rebate) {
        isRebate = rebate;
    }
}
