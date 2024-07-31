package com.example.biservice.payload.inverter;

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
public class InverterDto extends ProductDto {

    @NotNull(message = "Please provide no. of battery required")
    @Min(value = 1, message = "Value must be positive")
    private Integer noOfBatteryReq;

    @NotNull(message = "Please provide inverter warranty")
    @Min(value = 1, message = "Value must be positive")
    private Integer warranty;

    @NotBlank(message = "Please provide inverter-type")
    private String inverterType;

    @NotBlank(message = "Please provide recommended battery capacity")
    private String recBatteryCapacity;

    public InverterDto(Integer productId, @NotBlank(message = "Please provide battery product name") String productName, @NotBlank(message = "Please provide battery model name") String modelName, @NotBlank(message = "Please provide battery brand name") String brandName, @Min(value = 0, message = "Value must be positive") int voltage, String imageMainUrl, String imageOneUrl, String imageTwoUrl, String imageThreeUrl, @NotNull(message = "Please provide battery mrp") @Min(value = 0, message = "Value must be positive") float mrp, @NotNull(message = "Please provide battery discount percentage") @Min(value = 0, message = "Value must be positive") float discountPercentage, @NotNull(message = "Please provide battery stock") @Min(value = 0, message = "Value must be positive") int stock, ProductType productType, @Min(value = 0, message = "Value must be positive") float rebate, Integer noOfBatteryReq, Integer warranty, String inverterType, String recBatteryCapacity) {
        super(productId, productName, modelName, brandName, voltage, imageMainUrl, imageOneUrl, imageTwoUrl, imageThreeUrl, mrp, discountPercentage, stock, productType, rebate);
        this.noOfBatteryReq = noOfBatteryReq;
        this.warranty = warranty;
        this.inverterType = inverterType;
        this.recBatteryCapacity = recBatteryCapacity;
    }
}
