package com.example.biservice.payload.commercial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommercialVSegmentDto {

    private Integer segmentId;

    @NotBlank(message = "Please provide a segment name")
    private String segmentName;
}
