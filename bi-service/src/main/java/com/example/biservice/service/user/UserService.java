package com.example.biservice.service.user;

import com.example.biservice.payload.user.ChangePasswordRequest;
import com.example.biservice.payload.user.UserDto;

import java.security.Principal;

public interface UserService {

    UserDto getUserById(Long userId);

    UserDto getUserByPhoneNumber(String phoneNumber);

    void activateOrDeactivateUser(int value, Long userId);

    UserDto updateUser(UserDto userDto, Long userId);

    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
