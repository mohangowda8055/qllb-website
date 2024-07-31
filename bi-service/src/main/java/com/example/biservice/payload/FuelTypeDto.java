package com.example.biservice.payload;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeDto {

    private Integer fuelTypeId;

    private String fuelType;
}
