package com.example.biservice.payload.threewheeler;

import com.example.biservice.entity.ProductType;
import com.example.biservice.payload.ProductDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ThreeVBatteryDto extends ProductDto {

    @NotNull(message = "Please provide battery guarantee")
    @Min(value = 0, message = "Value must be positive")
    private int guarantee;

    @NotNull(message = "Please provide battery warranty")
    @Min(value = 0, message = "Value must be positive")
    private  int warranty;

    private String series;

    @Min(value = 0, message = "Value must be positive")
    private  int ah;

    private String terminalLayout;

    public ThreeVBatteryDto(Integer productId, @NotBlank(message = "Please provide battery product name") String productName, @NotBlank(message = "Please provide battery model name") String modelName, @NotBlank(message = "Please provide battery brand name") String brandName, @Min(value = 0, message = "Value must be positive") int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, @NotNull(message = "Please provide battery mrp") @Min(value = 0, message = "Value must be positive") float mrp, @NotNull(message = "Please provide battery discount percentage") @Min(value = 0, message = "Value must be positive") float discountPercentage, @NotNull(message = "Please provide battery stock") @Min(value = 0, message = "Value must be positive") int stock, ProductType productType, @Min(value = 0, message = "Value must be positive") float rebate, int guarantee, int warranty, String series, int ah, String terminalLayout) {
        super(productId, productName, modelName, brandName, voltage, imageMainUrl, imageOneUrl, imageTwoUrl, imageThreeUrl, mrp, discountPercentage, stock, productType, rebate);
        this.guarantee = guarantee;
        this.warranty = warranty;
        this.series = series;
        this.ah = ah;
        this.terminalLayout = terminalLayout;
    }
}
