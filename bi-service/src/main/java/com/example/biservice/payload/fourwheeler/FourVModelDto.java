package com.example.biservice.payload.fourwheeler;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FourVModelDto {

    private Integer modelId;

    @NotBlank(message = "Please provide a model name")
    private String modelName;
}
