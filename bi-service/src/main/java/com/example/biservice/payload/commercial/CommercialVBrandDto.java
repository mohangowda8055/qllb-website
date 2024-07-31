package com.example.biservice.payload.commercial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommercialVBrandDto {

    private Integer brandId;

    @NotBlank(message = "Please provide a brand name")
    private  String brandName;
}
