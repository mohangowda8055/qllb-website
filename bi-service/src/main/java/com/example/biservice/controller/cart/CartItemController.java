package com.example.biservice.controller.cart;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.cart.CartItemDto;
import com.example.biservice.payload.cart.CartItemResponseDto;
import com.example.biservice.service.cart.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/carts/{cartId}/products/{productId}/cartitems")
    public ResponseEntity<ApiResponse<CartItemDto>> createCartItem(@PathVariable Long cartId, @PathVariable Integer productId, @RequestBody CartItemDto cartItemDto){
        CartItemDto data = this.cartItemService.createCartItem(cartItemDto, cartId, productId);
        ApiResponse<CartItemDto> apiResponse = ApiResponse.<CartItemDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Cart item created")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/carts/{cartId}/cartitems")
    public ResponseEntity<ApiResponse<List<CartItemResponseDto>>> getAllCartItems(@PathVariable Long cartId){
        List<CartItemResponseDto> data = this.cartItemService.getAllCartItems(cartId);
        ApiResponse<List<CartItemResponseDto>> apiResponse = ApiResponse.<List<CartItemResponseDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found cart items")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/carts/{cartId}/products/{productId}/cartitems")
    public ResponseEntity<ApiResponse<CartItemDto>> getCartItem(@PathVariable Long cartId, @PathVariable Integer productId){
        CartItemDto data = this.cartItemService.getCartItem(cartId,productId);
        ApiResponse<CartItemDto> apiResponse = ApiResponse.<CartItemDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found cart item")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/carts/{cartId}/products/{productId}/cartitems")
    public ResponseEntity<ApiResponse<CartItemDto>> updateCartItem(@PathVariable Long cartId, @PathVariable Integer productId, @RequestBody CartItemDto cartItemDto){
        CartItemDto data = this.cartItemService.updateCartItem(cartItemDto,cartId,productId);
        ApiResponse<CartItemDto> apiResponse = ApiResponse.<CartItemDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated cart item")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/carts/{cartId}/products/{productId}/cartitems")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long cartId, @PathVariable Integer productId){
        this.cartItemService.deleteCartItem(cartId,productId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted cart item")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/carts/{cartId}/cartitems")
    public ResponseEntity<ApiResponse<Void>> deleteAllCartItems(@PathVariable Long cartId){
        this.cartItemService.deleteAllCartItems(cartId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Deleted all cart items")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
