package com.example.biservice.service.user;

import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.user.ChangePasswordRequest;
import com.example.biservice.payload.user.UserDto;
import com.example.biservice.repository.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final ModelMapper modelMapper;

    private final UserRepo userRepo;

    private  final PasswordEncoder passwordEncoder;


    @Override
    public UserDto getUserById(Long userId) {
      User user =  this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot find user - no existing user with user id "+userId));
      return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        User user = this.userRepo.findByPhoneNumber(phoneNumber).orElseThrow(()->new ResourceNotFoundException("Cannot find user - no existing user with phone number "+phoneNumber));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public void activateOrDeactivateUser(int value, Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot activate or deactivate user - no existing user with user id "+userId));
        user.setAddresses(user.getAddresses());
        user.setActive(value == 1);
        this.userRepo.save(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        Optional<User> optionalUser = this.userRepo.findById(userId);
        if(optionalUser.isPresent()) {
            User user = this.modelMapper.map(userDto, User.class);
            user.setActive(optionalUser.get().isActive());
            user.setAddresses(optionalUser.get().getAddresses());
            User updatedUser;
            try {
                updatedUser = this.userRepo.save(user);
            } catch (DataIntegrityViolationException e) {
                throw new UniqueConstraintException("An error occurred while updating the user - Duplicate Entry detected");
            }
            return this.modelMapper.map(updatedUser, UserDto.class);
        }else{
            throw new ResourceNotFoundException("Cannot update user - no existing user found with user id "+userId);
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if(!this.passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong password");
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new IllegalStateException("Passwords are not the same");
        }
        user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
        this.userRepo.save(user);
    }
}
