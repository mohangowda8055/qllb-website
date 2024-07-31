package com.example.biservice.payload.fourwheeler;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FourVBrandDto {

    private Integer brandId;

    @NotBlank(message = "Please provide a brand name")
    private  String brandName;
}
