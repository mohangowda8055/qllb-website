package com.example.biservice.controller.cart;

import com.example.biservice.payload.cart.CartItemDto;
import com.example.biservice.payload.cart.CartItemIdDto;
import com.example.biservice.payload.cart.CartItemResponseDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.cart.CartItemService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartItemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CartItemControllerTest {

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
    private CartItemService cartItemService;

    private CartItemDto mockCartItemDto;

    @BeforeEach
    void setUp() {
        mockCartItemDto = CartItemDto.builder()
                .id(new CartItemIdDto(1L,1))
                .quantity(2)
                .isRebate(true)
                .build();
    }

    @AfterEach
    void tearDown() {
        mockCartItemDto = null;
    }

    @Test
    public void cartItemController_createCartItem_returnCreated() throws Exception{
        //given
        CartItemDto inputCart = CartItemDto.builder()
                .quantity(2)
                .isRebate(true)
                .build();
        when(cartItemService.createCartItem(any(CartItemDto.class),anyLong(),anyInt())).thenReturn(mockCartItemDto);

        //when&then
        mockMvc.perform(post("/api/v1/cart/carts/1/products/1/cartitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputCart)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.quantity").value(inputCart.getQuantity()));
    }

    @Test
    public void cartItemController_getAllCartItems_returnCartItemList() throws Exception{
        //given
        List<CartItemResponseDto> cartItems = List.of(
                CartItemResponseDto.builder()
                        .id(new CartItemIdDto(1L,1))
                        .quantity(2)
                        .isRebate(true)
                        .build(),
                CartItemResponseDto.builder()
                        .id(new CartItemIdDto(2L,2))
                        .quantity(2)
                        .isRebate(true)
                        .build()
        );
        when(cartItemService.getAllCartItems(anyLong())).thenReturn(cartItems);

        //when&then
        mockMvc.perform(get("/api/v1/cart/carts/1/cartitems"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void cartItemController_getCartItem_returnCartItem() throws Exception{
        //given
        when(cartItemService.getCartItem(anyLong(),anyInt())).thenReturn(mockCartItemDto);

        //when&then
        mockMvc.perform(get("/api/v1/cart/carts/1/products/1/cartitems"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.quantity").value(mockCartItemDto.getQuantity()));
    }

    @Test
    public void cartItemController_updateCartItem_returnCartItem() throws Exception{
        //given
        CartItemDto cartItemToBeUpdated = CartItemDto.builder()
                .id(new CartItemIdDto(1L,1))
                .quantity(1)
                .isRebate(true)
                .build();
        when(cartItemService.updateCartItem(any(CartItemDto.class),anyLong(),anyInt())).thenReturn(cartItemToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/cart/carts/1/products/1/cartitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.quantity").value(cartItemToBeUpdated.getQuantity()));
    }

    @Test
    public void cartItemController_deleteCartItem_returnNull() throws Exception{
        //given
        doNothing().when(cartItemService).deleteCartItem(anyLong(),anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/cart/carts/1/products/1/cartitems"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void cartItemController_deleteAllCartItems_returnNull() throws Exception{
        //given
        doNothing().when(cartItemService).deleteAllCartItems(anyLong());

        //when&then
        mockMvc.perform(delete("/api/v1/cart/carts/1/cartitems"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}