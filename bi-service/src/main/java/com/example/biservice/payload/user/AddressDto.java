package com.example.biservice.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private AddressIdDto id;

    @NotBlank(message = "Address Line 1 is required")
    @Size(max = 50, message = "Address Line 1 must be at most 50 characters")
    private String addressLine1;

    @Size(max = 50, message = "Address Line 2 must be at most 50 characters")
    private String addressLine2;

    @NotBlank(message = "Please provide City name")
    @Size(max = 40, message = "City must be at most 40 characters")
    private String city;

    @NotBlank(message = "Please provide State name")
    @Size(max = 40, message = "State must be at most 40 characters")
    private String state;

    @NotBlank(message = "Please provide Postal Code")
    @Pattern(regexp = "^\\d{6}$", message = "Invalid Postal Code format")
    private String postalCode;

    @NotBlank(message = "Please provide a phone number")
    private String phoneNumber;

    @NotBlank(message = "Address Type is required")
    private String addressType;
}
