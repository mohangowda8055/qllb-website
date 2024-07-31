package com.example.biservice.payload.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private boolean success;

    private String token;

    private Long userId;

    private int status;

    private String message;
}
