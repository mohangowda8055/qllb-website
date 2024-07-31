package com.example.biservice.controller.product;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.ProductResponseDto;
import com.example.biservice.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{userId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> findProductById(@PathVariable Integer productId){
        ProductResponseDto data = this.productService.findProductById(productId);
        ApiResponse<ProductResponseDto> apiResponse = ApiResponse.<ProductResponseDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Found Product")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
