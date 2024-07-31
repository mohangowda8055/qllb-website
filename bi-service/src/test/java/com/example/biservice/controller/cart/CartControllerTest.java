package com.example.biservice.controller.cart;

import com.example.biservice.payload.cart.CartDto;
import com.example.biservice.payload.cart.CartItemResponseDto;
import com.example.biservice.payload.cart.CartResponseDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.cart.CartService;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CartControllerTest {

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
    private CartService cartService;

    private CartDto mockCartDto;

    private CartResponseDto mockCartResponseDto;

    @BeforeEach
    void setUp() {
        mockCartDto = CartDto.builder()
                .cartId(1L)
                .deliveryCost(0)
                .total(2000)
                .build();
        mockCartResponseDto = CartResponseDto.builder()
                .cartId(1L)
                .total(3000)
                .deliveryCost(50)
                .build();
    }

    @AfterEach
    void tearDown(){
        mockCartDto = null;
    }

    @Test
    public void cartController_createCart_returnCreated() throws Exception{
        //given
        when(cartService.createCart(anyLong())).thenReturn(mockCartDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/cart/admin/users/1/carts"));
        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.total").value(mockCartDto.getTotal()));
    }

    @Test
    public void cartController_getCartByUser_returnCart() throws Exception{
        //given
        when(cartService.getCartByUser(anyLong())).thenReturn(mockCartResponseDto);

        //when&then
        mockMvc.perform(get("/api/v1/cart/users/1/carts"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.cartId").value(mockCartDto.getCartId()));
    }

    @Test
    public void cartController_updateCart_returnUpdated() throws Exception{
        //given
        CartDto cartToBeUpdated = CartDto.builder()
                .cartId(1L)
                .deliveryCost(50)
                .total(2050)
                .build();
        when(cartService.updateCart(anyLong())).thenReturn(cartToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/cart/users/1/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.deliveryCost").value(cartToBeUpdated.getDeliveryCost()));
    }
}