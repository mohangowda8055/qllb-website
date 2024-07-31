package com.example.biservice.service.user;

import com.example.biservice.payload.user.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto createAddress(AddressDto addressDto, Long userId);

    List<AddressDto> getAddressByUser(Long userId);

    AddressDto getAddressByUserAndAddressType(Long userId, String addressType);

    AddressDto updateAddress(AddressDto addressDto, Long userId);

    AddressDto updateAddressSameAsBilling(Long userId);
}
