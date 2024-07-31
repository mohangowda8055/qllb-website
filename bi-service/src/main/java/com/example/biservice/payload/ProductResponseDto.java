package com.example.biservice.payload;

import com.example.biservice.entity.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Integer productId;

    private String productName;

    private String modelName;

    private String brandName;

    private int voltage;

    private  String imageMainUrl;

    private String imageOneUrl;

    private String imageTwoUrl;

    private String imageThreeUrl;

    private  float mrp;

    private float discountPercentage;

    private int stock;

    private float rebate;

    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    public ProductResponseDto(String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, float rebate, ProductType productType) {
        this.productName = productName;
        this.modelName = modelName;
        this.brandName = brandName;
        this.voltage = voltage;
        this.imageMainUrl = imageMainUrl;
        this.imageOneUrl = imageOneUrl;
        this.imageTwoUrl = imageTwoUrl;
        this.imageThreeUrl = imageThreeUrl;
        this.mrp = mrp;
        this.discountPercentage = discountPercentage;
        this.stock = stock;
        this.rebate = rebate;
        this.productType = productType;
    }
}
