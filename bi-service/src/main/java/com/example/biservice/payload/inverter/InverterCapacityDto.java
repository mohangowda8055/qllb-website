package com.example.biservice.payload.inverter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InverterCapacityDto {

    private Integer capacityId;

    @NotNull(message = "Please provide inverter-capacity")
    @Min(value = 1, message = "Value must be positive")
    private Integer capacity;
}
