package com.example.biservice.service.cart;

import com.example.biservice.payload.cart.CartDto;
import com.example.biservice.payload.cart.CartResponseDto;

public interface CartService {

    CartDto createCart(Long userId);

    CartResponseDto getCartByUser(Long userId);

    CartDto updateCart(Long userId);

    CartDto updateCartWithItems(Long userId, CartResponseDto cartResponseDto);

    CartDto updatePincode(Long userId, CartDto cartDto );

}
