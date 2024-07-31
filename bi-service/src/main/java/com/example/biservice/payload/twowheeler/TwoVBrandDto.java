package com.example.biservice.payload.twowheeler;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoVBrandDto {

    private Integer brandId;

    @NotBlank(message = "Please provide a brand name")
    private  String brandName;

}
