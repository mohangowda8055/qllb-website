package com.example.biservice.payload;

import com.example.biservice.entity.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductDto {

    private Integer productId;

    @NotBlank(message = "Please provide battery product name")
    private String productName;

    @NotBlank(message = "Please provide battery model name")
    private String modelName;

    @NotBlank(message = "Please provide battery brand name")
    private String brandName;

    @Min(value = 0, message = "Value must be positive")
    private int voltage;

    private  String imageMainUrl;

    private String imageOneUrl;

    private String imageTwoUrl;

    private String imageThreeUrl;

    @NotNull(message = "Please provide battery mrp")
    @Min(value = 0, message = "Value must be positive")
    private  float mrp;

    @NotNull(message = "Please provide battery discount percentage")
    @Min(value = 0, message = "Value must be positive")
    private float discountPercentage;

    @NotNull(message = "Please provide battery stock")
    @Min(value = 0, message = "Value must be positive")
    private int stock;

    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @Min(value = 0, message = "Value must be positive")
    private float rebate;

    public ProductDto(String productName, String modelName, String brandName, int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, float mrp, float discountPercentage, int stock, ProductType productType, float rebate) {
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
        this.productType = productType;
        this.rebate = rebate;
    }
}
