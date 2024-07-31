package com.example.biservice.service.product;

import com.example.biservice.payload.ProductResponseDto;

public interface ProductService {

    ProductResponseDto findProductById(Integer productId);
}
