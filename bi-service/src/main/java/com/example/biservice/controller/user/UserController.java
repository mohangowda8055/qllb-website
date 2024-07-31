package com.example.biservice.controller.user;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.user.ChangePasswordRequest;
import com.example.biservice.payload.user.UserDto;
import com.example.biservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/users/id/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId){
        UserDto data = this.userService.getUserById(userId);
        ApiResponse<UserDto> apiResponse = ApiResponse.<UserDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("User Found")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/phone/users")
    public ResponseEntity<ApiResponse<UserDto>> getUserByPhoneNumber(@RequestParam(value = "phone") String phoneNumber){
        UserDto data = this.userService.getUserByPhoneNumber(phoneNumber);
        ApiResponse<UserDto> apiResponse = ApiResponse.<UserDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("User Found by phone number")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/users/{userId}/isActive/{isActive}")
    public ResponseEntity<ApiResponse<Void>> activateOrDeactivateUser(@PathVariable Long userId, @PathVariable int isActive){
        this.userService.activateOrDeactivateUser(isActive, userId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message(isActive == 1 ? "User activated" : "User deactivated")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto){
        UserDto data = this.userService.updateUser(userDto, userId);
        ApiResponse<UserDto> apiResponse = ApiResponse.<UserDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("User updated")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser){
        this.userService.changePassword(request, connectedUser);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("Successfully changed user password")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
