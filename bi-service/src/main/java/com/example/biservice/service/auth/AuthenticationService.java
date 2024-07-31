package com.example.biservice.service.auth;

import com.example.biservice.payload.auth.AuthenticationRequest;
import com.example.biservice.payload.auth.AuthenticationResponse;
import com.example.biservice.payload.user.UserDto;

public interface AuthenticationService {

    AuthenticationResponse register(UserDto userDto);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
