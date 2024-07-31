package com.example.biservice.payload.twowheeler;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoVModelDto {

    private Integer modelId;

    @NotBlank(message = "Please provide a model name")
    private String modelName;

}
