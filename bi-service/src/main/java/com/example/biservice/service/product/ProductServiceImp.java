package com.example.biservice.service.product;

import com.example.biservice.entity.Product;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.ProductResponseDto;
import com.example.biservice.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto findProductById(Integer productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Cannot find product - no existing product found with id " + productId));
        return this.modelMapper.map(product, ProductResponseDto.class);
    }
}
