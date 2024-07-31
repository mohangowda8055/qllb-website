package com.example.biservice.controller.user;

import com.example.biservice.payload.user.AddressDto;
import com.example.biservice.payload.user.AddressIdDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.user.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private LogoutHandler logoutHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    private AddressDto mockAddressDto;

    @BeforeEach
    void setUp() {
        mockAddressDto = AddressDto.builder()
                .id(new AddressIdDto("Billing",1L))
                .addressLine1("abc")
                .city("city")
                .state("state")
                .postalCode("000000")
                .phoneNumber("1234567890")
                .addressType("Billing")
                .build();
    }

    @AfterEach
    void tearDown() {
        mockAddressDto = null;
    }

    @Test
    public void addressController_createAddress_returnCreated() throws Exception{
        //given
        AddressDto inputAddress = AddressDto.builder()
                .addressLine1("abc")
                .city("city")
                .state("state")
                .postalCode("000000")
                .phoneNumber("1234567890")
                .addressType("Billing")
                .build();
        when(addressService.createAddress(any(AddressDto.class),anyLong())).thenReturn(mockAddressDto);

        //when&then
        mockMvc.perform(post("/api/v1/user/users/1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputAddress)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.city").value(inputAddress.getCity()));
    }

    @Test
    public void addressController_createAddress_returnBadRequest() throws Exception{
        //given
        AddressDto inputAddress = AddressDto.builder()
                .addressLine1("abc")
                .city("city")
                .state("state")
                .phoneNumber("1234567890")
                .addressType("Billing")
                .build();
        when(addressService.createAddress(any(AddressDto.class),anyLong())).thenReturn(mockAddressDto);

        //when&then
        mockMvc.perform(post("/api/v1/user/users/1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputAddress)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addressController_getAddressByUser_returnAddressList() throws Exception{
        //given
        List<AddressDto> addressDtoList = List.of(
                AddressDto.builder()
                        .id(new AddressIdDto("Billing",1L))
                        .addressLine1("abc")
                        .city("city")
                        .state("state")
                        .postalCode("000000")
                        .phoneNumber("1234567890")
                        .addressType("Billing")
                        .build(),
                AddressDto.builder()
                        .id(new AddressIdDto("Delivery",1L))
                        .addressLine1("abc")
                        .city("city")
                        .state("state")
                        .postalCode("000000")
                        .phoneNumber("1234567890")
                        .addressType("Delivery")
                        .build()
        );
        when(addressService.getAddressByUser(anyLong())).thenReturn(addressDtoList);

        //when&then
        mockMvc.perform(get("/api/v1/user/users/1/addresses"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void addressController_getAddressByUserAndAddressType_returnAddress() throws Exception{
        //given
        when(addressService.getAddressByUserAndAddressType(anyLong(),anyString())).thenReturn(mockAddressDto);

        //when&then
        mockMvc.perform(get("/api/v1/user/users/1/type/Billing/addresses"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.addressType").value(mockAddressDto.getAddressType()));
    }

    @Test
    public void addressController_updateAddress_returnAddress() throws Exception{
        //given
        AddressDto addressToBeUpdated = AddressDto.builder()
                .id(new AddressIdDto("Billing",1L))
                .addressLine1("abc")
                .city("city")
                .state("state")
                .postalCode("000000")
                .phoneNumber("1234567890")
                .addressType("Delivery")
                .build();
        when(addressService.updateAddress(any(AddressDto.class),anyLong())).thenReturn(addressToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/user/users/1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.addressType").value(addressToBeUpdated.getAddressType()));
    }

    @Test
    public void addressController_updateAddress_returnBadRequest() throws Exception{
        //given
        AddressDto addressToBeUpdated = AddressDto.builder()
                .id(new AddressIdDto("Billing",1L))
                .addressLine1("abc")
                .city("city")
                .state("state")
                .phoneNumber("1234567890")
                .addressType("Delivery")
                .build();
        when(addressService.updateAddress(any(AddressDto.class),anyLong())).thenReturn(addressToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/user/users/1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addressController_updateDeliveryAddressSameAsBilling_returnAddress() throws Exception{
        //given
        AddressDto deliveryAddress = AddressDto.builder()
                .id(new AddressIdDto("Delivery",1L))
                .addressLine1("abc")
                .city("city")
                .state("state")
                .postalCode("000000")
                .phoneNumber("1234567890")
                .addressType("Delivery")
                .build();
        when(addressService.updateAddressSameAsBilling(anyLong())).thenReturn(deliveryAddress);

        //when&then
        mockMvc.perform(put("/api/v1/user/users/1/delivery/addresses"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.addressType").value(deliveryAddress.getAddressType()));
    }
}