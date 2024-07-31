package com.example.biservice.service.cart;

import com.example.biservice.payload.cart.CartItemDto;
import com.example.biservice.payload.cart.CartItemResponseDto;

import java.util.List;

public interface CartItemService {

    CartItemDto createCartItem(CartItemDto cartItemDto, Long cartId, Integer productId);

    List<CartItemResponseDto> getAllCartItems(Long cartId);

    CartItemDto getCartItem(Long cartId, Integer productId);

    CartItemDto updateCartItem(CartItemDto cartItemDto, Long cartId, Integer productId);

    void deleteCartItem(Long cartId, Integer productId);

    void deleteAllCartItems(Long cartId);
}
