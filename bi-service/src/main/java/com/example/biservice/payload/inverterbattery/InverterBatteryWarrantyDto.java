package com.example.biservice.payload.inverterbattery;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InverterBatteryWarrantyDto {

    private Integer warrantyId;

    @NotNull(message = "Please provide battery warranty")
    @Min(value = 1, message = "Value must be positive")
    private Integer warranty;

    @NotNull(message = "Please provide battery guarantee")
    @Min(value = 1, message = "Value must be positive")
    private Integer guarantee;
}
