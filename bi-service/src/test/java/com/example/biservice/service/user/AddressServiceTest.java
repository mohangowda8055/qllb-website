package com.example.biservice.service.user;

import com.example.biservice.entity.user.Address;
import com.example.biservice.entity.user.AddressId;
import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.user.AddressDto;
import com.example.biservice.payload.user.AddressIdDto;
import com.example.biservice.repository.user.AddressRepo;
import com.example.biservice.repository.user.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AddressServiceImp addressServiceImp;

    private AddressDto mockAddressDto;

    private Address mockAddress;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .userId(1L)
                .build();
        mockAddress = Address.builder()
                .id(new AddressId("Billing",1L))
                .addressLine1("Address Line 1")
                .addressType("Billing")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        mockAddressDto = AddressDto.builder()
                .id(new AddressIdDto("Billing",1L))
                .addressLine1("Address Line 1")
                .addressType("Billing")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        when(modelMapper.map(any(AddressDto.class),eq(Address.class))).thenReturn(mockAddress);
        when(modelMapper.map(any(Address.class),eq(AddressDto.class))).thenReturn(mockAddressDto);
    }

    @AfterEach
    void tearDown() {
        mockUser = null;
        mockAddress = null;
        mockAddressDto = null;
    }

    @Test
    public void addressService_createAddress_returnAddress(){
        //given
        Long userId = 1L;
        AddressDto inputAddressDto = AddressDto.builder()
                .addressLine1("Address Line 1")
                .addressType("Billing")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        Address inputAddress = Address.builder()
                .addressLine1("Address Line 1")
                .addressType("Billing")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        when(modelMapper.map(any(AddressDto.class),eq(Address.class))).thenReturn(inputAddress);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(addressRepo.save(any(Address.class))).thenReturn(mockAddress);

        //when
        AddressDto response = addressServiceImp.createAddress(inputAddressDto,userId);

        //then
        assertNotNull(response);
        assertEquals(inputAddressDto.getAddressLine1(),response.getAddressLine1());
    }

    @Test
    public void addressService_createAddress_throwResourceNotFoundException(){
        //given
        Long userId = 1L;
        AddressDto inputAddressDto = AddressDto.builder()
                .addressLine1("Address Line 1")
                .addressType("Billing")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        Address inputAddress = Address.builder()
                .addressLine1("Address Line 1")
                .addressType("Billing")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        when(modelMapper.map(any(AddressDto.class),eq(Address.class))).thenReturn(inputAddress);
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(addressRepo.save(any(Address.class))).thenReturn(mockAddress);

        //when&then
        assertThrows(ResourceNotFoundException.class,()->addressServiceImp.createAddress(inputAddressDto,userId));
    }

    @Test
    public void addressService_getAddressByUser_returnAddressList(){
        //given
        Long userId = 1L;
        List<Address> addresses = List.of(
                Address.builder()
                        .addressLine1("Address Line 1")
                        .addressType("Billing")
                        .state("State")
                        .city("City")
                        .phoneNumber("1234567890")
                        .postalCode("000000")
                        .build(),
                Address.builder()
                        .addressLine1("Address Line 1")
                        .addressType("Delivery")
                        .state("State")
                        .city("City")
                        .phoneNumber("1234567890")
                        .postalCode("000000")
                        .build()
        );
        when(addressRepo.findByIdUserId(anyLong())).thenReturn(addresses);
        when(modelMapper.map(any(Address.class),eq(AddressDto.class)))
                .thenAnswer(invocation ->{
                    Address address = invocation.getArgument(0);
                    return AddressDto.builder()
                            .addressType(address.getAddressType())
                            .build();
                });
        //when
        List<AddressDto> result = addressServiceImp.getAddressByUser(userId);
        //then
        assertEquals(addresses.size(), result.size());
        assertEquals("Billing", result.get(0).getAddressType());// Assert sorted order
        assertEquals("Delivery", result.get(1).getAddressType());
    }

    @Test
    public void addressService_getAddressByUserAndAddressType_returnAddress(){
        //given
        Long userId = 1L;
        String addressType = "Billing";
        when(addressRepo.findByIdAddressTypeAndIdUserId(anyString(),anyLong())).thenReturn(Optional.of(mockAddress));

        //when
        AddressDto response = addressServiceImp.getAddressByUserAndAddressType(userId,addressType);
        //then
        assertNotNull(response);
        assertEquals(mockAddressDto.getAddressLine1(),response.getAddressLine1());
    }

    @Test
    public void addressService_updateAddress_returnAddress(){
        //given
        Long userId = 1L;
        AddressDto addressToBeUpdatedDto = AddressDto.builder()
                .id(new AddressIdDto("Billing",1L))
                .addressLine1("Address Line 1")
                .addressType("Delivery")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        Address addressToBeUpdated = Address.builder()
                .id(new AddressId("Billing",1L))
                .addressLine1("Address Line 1")
                .addressType("Delivery")
                .state("State")
                .city("City")
                .phoneNumber("1234567890")
                .postalCode("000000")
                .build();
        when(modelMapper.map(any(AddressDto.class),eq(Address.class))).thenReturn(addressToBeUpdated);
        when(modelMapper.map(any(Address.class),eq(AddressDto.class))).thenReturn(addressToBeUpdatedDto);
        when(addressRepo.findByIdAddressTypeAndIdUserId(anyString(),anyLong())).thenReturn(Optional.of(mockAddress));
        when(addressRepo.save(any(Address.class))).thenReturn(addressToBeUpdated);

        //when
        AddressDto response = addressServiceImp.updateAddress(addressToBeUpdatedDto,userId);
        //then
        assertNotNull(response);
        assertEquals(addressToBeUpdatedDto.getAddressType(), response.getAddressType());
    }

    @Test
    public void addressService_updateAddressSameAsBilling_returnAddress(){
        //given
        Long userId = 1L;
        when(addressRepo.findByIdAddressTypeAndIdUserId(anyString(),anyLong())).thenReturn(Optional.of(mockAddress));
        when(addressRepo.save(any(Address.class))).thenReturn(mockAddress);

        //when
        AddressDto response = addressServiceImp.updateAddressSameAsBilling(userId);
        //then
        assertNotNull(response);
    }
}