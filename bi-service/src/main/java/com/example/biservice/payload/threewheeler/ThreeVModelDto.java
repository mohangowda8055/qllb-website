package com.example.biservice.payload.threewheeler;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreeVModelDto {

    private Integer modelId;

    @NotBlank(message = "Please provide a model name")
    private String modelName;
}
