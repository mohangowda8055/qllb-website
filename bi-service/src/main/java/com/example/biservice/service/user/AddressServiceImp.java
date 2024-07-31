package com.example.biservice.service.user;

import com.example.biservice.entity.user.Address;
import com.example.biservice.entity.user.AddressId;
import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.user.AddressDto;
import com.example.biservice.repository.user.AddressRepo;
import com.example.biservice.repository.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService{

    private final ModelMapper modelMapper;

    private final AddressRepo addressRepo;

    private final UserRepo userRepo;


    @Override
    public AddressDto createAddress(AddressDto addressDto, Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot find user - no existing user with user id "+userId));
        Address address = this.modelMapper.map(addressDto, Address.class);
        AddressId addressId = new AddressId("BILLING".equalsIgnoreCase(address.getAddressType()) ? "BILLING" : "DELIVERY", user.getUserId());
        address.setId(addressId);
        Address savedAddress = this.addressRepo.save(address);
        return this.modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAddressByUser(Long userId) {
       List<Address> addresses = this.addressRepo.findByIdUserId(userId);
       return addresses.stream().map((address)->modelMapper.map(address, AddressDto.class)).collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressByUserAndAddressType(Long userId, String addressType) {
        String type = addressType.toUpperCase();
        Address address = this.addressRepo.findByIdAddressTypeAndIdUserId(type, userId).orElseThrow(()->new ResourceNotFoundException("Cannot find address - no existing user id or address type "+addressType));
        return this.modelMapper.map(address, AddressDto.class);
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto, Long userId) {
        Optional<Address> optionalAddress = this.addressRepo.findByIdAddressTypeAndIdUserId(addressDto.getId().getAddressType(), userId);
        if(optionalAddress.isPresent()) {
            Address address = this.modelMapper.map(addressDto, Address.class);
            address.getId().setAddressType("BILLING".equalsIgnoreCase(address.getAddressType()) ? "BILLING" : "DELIVERY");
            address.setUser(optionalAddress.get().getUser());
            Address updatedAddress = this.addressRepo.save(address);
            return this.modelMapper.map(updatedAddress, AddressDto.class);
        }else {
            throw new ResourceNotFoundException("Cannot update address - no existing address found with address type and user id "+userId);
        }
    }

    @Override
    public AddressDto updateAddressSameAsBilling(Long userId) {
        Address address = this.addressRepo.findByIdAddressTypeAndIdUserId("BILLING", userId).orElseThrow(()->new ResourceNotFoundException("Cannot find address - no existing user id or address type BILLING"));
        AddressId addressId = new AddressId("DELIVERY", userId);
        Address address1 = Address.builder()
                .id(addressId)
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .phoneNumber(address.getPhoneNumber())
                .addressType("DELIVERY")
                .build();
        Address updatedAddress = this.addressRepo.save(address1);
        return this.modelMapper.map(updatedAddress, AddressDto.class);
    }
}
