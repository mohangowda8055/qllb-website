package com.example.biservice.security.service;

import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.repository.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepo.findByPhoneNumber(username).orElseThrow(()->new ResourceNotFoundException("Cannot login - user not found with phone number"));
    }
}
