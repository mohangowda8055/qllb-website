package com.example.biservice.payload.inverterbattery;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackupDurationDto {

    private Integer backupDurationId;

    @NotNull(message = "Please provide backup-duration")
    @Min(value = 1, message = "Value must be positive")
    private float backupDuration;
}
