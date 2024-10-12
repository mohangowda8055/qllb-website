package com.example.biservice.service.cart;

import com.example.biservice.entity.cart.Cart;
import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.cart.CartItemId;
import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.cart.CartDto;
import com.example.biservice.payload.cart.CartResponseDto;
import com.example.biservice.repository.cart.CartItemRepo;
import com.example.biservice.repository.cart.CartRepo;
import com.example.biservice.repository.user.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class CartServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CartItemRepo cartItemRepo;

    @InjectMocks
    private CartServiceImp cartServiceImp;

    private Cart mockCart;
    private CartDto mockCartDto;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser =  User.builder()
                .userId(1L)
                .build();
        mockCart = Cart.builder()
                .cartId(1L)
                .total(2000)
                .deliveryCost(50)
                .build();
        mockCartDto = CartDto.builder()
                .cartId(1L)
                .total(2000)
                .deliveryCost(50)
                .build();
        when(modelMapper.map(any(CartDto.class),eq(Cart.class))).thenReturn(mockCart);
        when(modelMapper.map(any(Cart.class),eq(CartDto.class))).thenReturn(mockCartDto);
    }

    @AfterEach
    void tearDown() {
        mockCart = null;
        mockCartDto = null;
    }

    @Test
    public void cartService_createCart_returnCart(){
        //given
        Long userId = 1L;
        mockCart = Cart.builder()
                .total(2000)
                .build();
        mockCartDto = CartDto.builder()
                .total(2000)
                .build();
        Cart savedCart = Cart.builder()
                .cartId(1L)
                .total(2000)
                .build();
        CartDto savedCartDto = CartDto.builder()
                .cartId(1L)
                .total(2000)
                .build();
        when(modelMapper.map(any(CartDto.class),eq(Cart.class))).thenReturn(savedCart);
        when(modelMapper.map(any(Cart.class),eq(CartDto.class))).thenReturn(savedCartDto);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(cartRepo.save(any(Cart.class))).thenReturn(savedCart);

        //when
        CartDto response = cartServiceImp.createCart(userId);
        //then
        assertNotNull(response);
        assertEquals(response.getTotal(),mockCartDto.getTotal());
    }

    @Test
    public void cartService_createCart_throwResourceNotFoundException(){
        //given
        Long userId = 1L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class,()->cartServiceImp.createCart(userId));
    }

    @Test
    public void cartService_getCartByUser_throwResourceNotFoundException(){
        //given
        Long userId = 1L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class,()->cartServiceImp.getCartByUser(userId));
    }

    @Test
    public void cartService_updateCart_returnCart(){
        //given
        Long userId = 1L;
        TwoVBattery product = TwoVBattery.builder()
                .productId(1)
                .modelName("abc")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(250.0000f)
                .guarantee(12)
                .warranty(12)
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
        List<CartItem> cartItems = List.of(
                CartItem.builder()
                        .id(new CartItemId(1L,1))
                        .quantity(2)
                        .isRebate(true)
                        .product(product)
                        .build(),
                CartItem.builder()
                        .id(new CartItemId(1L,1))
                        .quantity(2)
                        .isRebate(true)
                        .product(product)
                        .build()
        );
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(cartRepo.findByUser(any(User.class))).thenReturn(Optional.of(mockCart));
        when(cartItemRepo.findAllByIdCartId(anyLong())).thenReturn(cartItems);
        when(cartRepo.save(any(Cart.class))).thenReturn(mockCart);

        //when
        CartDto response = cartServiceImp.updateCart(userId);

        //then
        assertNotNull(response);
        assertEquals(response.getTotal(),mockCartDto.getTotal());
    }
}