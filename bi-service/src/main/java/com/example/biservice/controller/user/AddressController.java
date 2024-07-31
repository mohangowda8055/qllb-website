package com.example.biservice.controller.user;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.user.AddressDto;
import com.example.biservice.service.user.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/users/{userId}/addresses")
    public ResponseEntity<ApiResponse<AddressDto>> createAddress(@PathVariable Long userId, @Valid @RequestBody AddressDto addressDto){
        AddressDto data = this.addressService.createAddress(addressDto, userId);
        ApiResponse<AddressDto> apiResponse = ApiResponse.<AddressDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Address added")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/addresses")
    public ResponseEntity<ApiResponse<List<AddressDto>>> getAddressByUser(@PathVariable Long userId){
        List<AddressDto> data = this.addressService.getAddressByUser(userId);
        ApiResponse<List<AddressDto>> apiResponse = ApiResponse.<List<AddressDto>>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found addresses")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/type/{addressType}/addresses")
    public ResponseEntity<ApiResponse<AddressDto>> getAddressByUserAndType(@PathVariable Long userId, @PathVariable String addressType){
        AddressDto data = this.addressService.getAddressByUserAndAddressType(userId, addressType);
        ApiResponse<AddressDto> apiResponse = ApiResponse.<AddressDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found address by user and address type")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/users/{userId}/addresses")
    public ResponseEntity<ApiResponse<AddressDto>> updateAddress(@PathVariable Long userId, @Valid @RequestBody AddressDto addressDto){
        AddressDto data = this.addressService.updateAddress(addressDto,userId);
        ApiResponse<AddressDto> apiResponse = ApiResponse.<AddressDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated address")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/users/{userId}/delivery/addresses")
    public ResponseEntity<ApiResponse<AddressDto>> updateDeliveryAddressSameAsBilling(@PathVariable Long userId){
        AddressDto data = this.addressService.updateAddressSameAsBilling(userId);
        ApiResponse<AddressDto> apiResponse = ApiResponse.<AddressDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated delivery address same as billing address")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
