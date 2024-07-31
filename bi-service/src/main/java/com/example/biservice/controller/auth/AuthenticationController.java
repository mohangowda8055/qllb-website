package com.example.biservice.controller.auth;

import com.example.biservice.payload.auth.AuthenticationRequest;
import com.example.biservice.payload.auth.AuthenticationResponse;
import com.example.biservice.payload.user.UserDto;
import com.example.biservice.service.auth.AuthenticationService;
import com.example.biservice.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final CartService cartService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserDto userDto){
        AuthenticationResponse response = this.authenticationService.register(userDto);
        this.cartService.createCart(response.getUserId());
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User Registered Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = this.authenticationService.authenticate(request);
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User Authenticated Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
