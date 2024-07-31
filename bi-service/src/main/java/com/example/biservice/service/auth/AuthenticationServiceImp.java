package com.example.biservice.service.auth;

import com.example.biservice.entity.user.Role;
import com.example.biservice.entity.user.Token;
import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.auth.AuthenticationRequest;
import com.example.biservice.payload.auth.AuthenticationResponse;
import com.example.biservice.payload.user.UserDto;
import com.example.biservice.repository.user.TokenRepo;
import com.example.biservice.repository.user.UserRepo;
import com.example.biservice.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final ModelMapper modelMapper;

    private final UserRepo userRepo;
    
    private final TokenRepo tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        user.setRole(Role.USER);
        User savedUser;
        try {
            savedUser = this.userRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintException("An error occurred while saving user - Duplicate Entry detected");
        }
        var jwtToken = this.jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .userId(savedUser.getUserId())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token existingToken = this.tokenRepo.findById(user.getUserId()).orElse(null);
        if(existingToken != null){
            existingToken.setToken(jwtToken);
            this.tokenRepo.save(existingToken);
        }else {
            Token token = new Token();
            token.setUser(user);
            token.setToken(jwtToken);
            this.tokenRepo.save(token);
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
            );
     User user = this.userRepo.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(()->new ResourceNotFoundException("Login failed - no user found with phone number "+request.getPhoneNumber()));
        var jwtToken = this.jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .userId(user.getUserId())
                .build();
    }
}
