package com.example.biservice.payload.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressIdDto {

    private String addressType;

    private Long userId;
}
