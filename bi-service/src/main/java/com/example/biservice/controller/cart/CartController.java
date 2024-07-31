package com.example.biservice.controller.cart;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.cart.CartDto;
import com.example.biservice.payload.cart.CartItemDto;
import com.example.biservice.payload.cart.CartResponseDto;
import com.example.biservice.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping("/admin/users/{userId}/carts")
    public ResponseEntity<ApiResponse<CartDto>> createCart(@PathVariable Long userId){
        CartDto data = this.cartService.createCart(userId);
        ApiResponse<CartDto> apiResponse = ApiResponse.<CartDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Cart - for user with id "+userId)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/carts")
    public ResponseEntity<ApiResponse<CartResponseDto>> getCartByUser(@PathVariable Long userId){
        CartResponseDto data = this.cartService.getCartByUser(userId);
        ApiResponse<CartResponseDto> apiResponse = ApiResponse.<CartResponseDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Found Cart")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/users/{userId}/carts")
    public ResponseEntity<ApiResponse<CartDto>> updateCart(@PathVariable Long userId){
        CartDto data = this.cartService.updateCart(userId);
        ApiResponse<CartDto> apiResponse = ApiResponse.<CartDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Cart")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/users/{userId}/carts")
    public ResponseEntity<ApiResponse<CartDto>> updateCartWithCartItems(@PathVariable Long userId, @RequestBody CartResponseDto cartResponseDto){
      CartDto data = this.cartService.updateCartWithItems(userId, cartResponseDto);
        ApiResponse<CartDto> apiResponse = ApiResponse.<CartDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Cart with Cart items")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/users/{userId}/pincodes/carts")
    public ResponseEntity<ApiResponse<CartDto>> updateCartPincode(@PathVariable Long userId, @RequestBody CartDto cartDto){
       CartDto data =  this.cartService.updatePincode(userId, cartDto);
        ApiResponse<CartDto> apiResponse = ApiResponse.<CartDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated cart pincode with id "+userId)
                .build();
        return  ResponseEntity.ok(apiResponse);
    }
}
