package com.example.biservice.payload.threewheeler;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreeVBrandDto {

    private Integer brandId;

    @NotBlank(message = "Please provide a brand name")
    private  String brandName;
}
