package com.example.biservice.payload.commercial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommercialVModelDto {

    private Integer modelId;

    @NotBlank(message = "Please provide a model name")
    private String modelName;
}
