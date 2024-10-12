package com.example.biservice.service.cart;

import com.example.biservice.entity.cart.Cart;
import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.cart.CartItemId;
import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.cart.CartItemDto;
import com.example.biservice.payload.cart.CartItemIdDto;
import com.example.biservice.payload.cart.CartItemResponseDto;
import com.example.biservice.repository.ProductRepo;
import com.example.biservice.repository.cart.CartItemRepo;
import com.example.biservice.repository.cart.CartRepo;
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
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.profiles.active=test")
class CartItemServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CartItemRepo cartItemRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartItemServiceImp cartItemServiceImp;

    private CartItem mockCartItem;
    private CartItemDto mockCartItemDto;
    private Cart mockCart;

    private TwoVBattery mockProduct;

    @BeforeEach
    void setUp() {
        User mockUser = User.builder()
                .userId(1L)
                .build();
        mockCart = Cart.builder()
                .cartId(1L)
                .total(2000)
                .deliveryCost(50)
                .user(mockUser)
                .build();
        mockProduct = TwoVBattery.builder()
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
        mockCartItemDto = CartItemDto.builder()
                .id(new CartItemIdDto(1L,1))
                .quantity(2)
                .isRebate(true)
                .build();
        mockCartItem = CartItem.builder()
                .id(new CartItemId(1L,1))
                .quantity(2)
                .isRebate(true)
                .product(mockProduct)
                .cart(mockCart)
                .build();


        when(modelMapper.map(any(CartItemDto.class),eq(CartItem.class))).thenReturn(mockCartItem);
        when(modelMapper.map(any(CartItem.class),eq(CartItemDto.class))).thenReturn(mockCartItemDto);
    }

    @AfterEach
    void tearDown() {
        mockCartItem = null;
        mockCartItemDto = null;
        mockProduct = null;
    }

    @Test
    public void cartItemService_createCartItem_returnCartItem() {
        //
        Long cartId = 1L;
        Integer productId = 1;
        CartItemDto inputCartItem = CartItemDto.builder()
                .quantity(2)
                .isRebate(true)
                .build();
        when(cartItemRepo.findByIdCartIdAndIdProductId(anyLong(), anyInt())).thenReturn(Optional.empty());
        when(cartRepo.findById(anyLong())).thenReturn(Optional.of(mockCart));
        when(productRepo.findById(anyInt())).thenReturn(Optional.of(mockProduct));
        when(cartItemRepo.save(any(CartItem.class))).thenReturn(mockCartItem);

        //when
        CartItemDto response = cartItemServiceImp.createCartItem(inputCartItem, cartId, productId);

        //then
        assertNotNull(response);
        assertEquals(inputCartItem.getQuantity(),response.getQuantity());
    }

    @Test
    public void cartItemService_createCartItemAlreadyPresent_returnCartItem() {
        //
        Long cartId = 1L;
        Integer productId = 1;
        CartItemDto inputCartItem = CartItemDto.builder()
                .quantity(2)
                .isRebate(true)
                .build();
        when(cartItemRepo.findByIdCartIdAndIdProductId(anyLong(), anyInt())).thenReturn(Optional.of(mockCartItem));
        when(cartRepo.findById(anyLong())).thenReturn(Optional.of(mockCart));
        when(productRepo.findById(anyInt())).thenReturn(Optional.of(mockProduct));
        when(cartItemRepo.save(any(CartItem.class))).thenReturn(mockCartItem);
        when(cartService.updateCart(anyLong())).thenReturn(null);

        //when
        CartItemDto response = cartItemServiceImp.createCartItem(inputCartItem, cartId, productId);

        //then
        assertNotNull(response);
        assertEquals(4,response.getQuantity()+2);
    }

    @Test
    public void cartItemService_getAllCartItems_returnCartItemList(){
       //given
       Long cartId = 1L;
        List<CartItem> cartItems = List.of(
                CartItem.builder()
                        .id(new CartItemId(1L,1))
                        .quantity(2)
                        .isRebate(true)
                        .build(),
                CartItem.builder()
                        .id(new CartItemId(1L,2))
                        .quantity(2)
                        .isRebate(true)
                        .build()
        );
        when(cartItemRepo.findAllByIdCartId(anyLong())).thenReturn(cartItems);

        //when
       List<CartItemResponseDto> response = cartItemServiceImp.getAllCartItems(cartId);
       //then
        assertNotNull(response);
        assertEquals(2,response.size());
    }

    @Test
    public void cartItemService_getCartItem_returnCartItem(){
        //given
        Long cartId = 1L;
        Integer productId = 1;
        when(cartItemRepo.findByIdCartIdAndIdProductId(anyLong(),anyInt())).thenReturn(Optional.of(mockCartItem));

        //when
        CartItemDto response = cartItemServiceImp.getCartItem(cartId,productId);
        //then
        assertNotNull(response);
        assertEquals(mockCartItem.getQuantity(),response.getQuantity());
    }

    @Test
    public void cartItemService_updateCartItem_returnCartItem(){
        //given
        Long cartId = 1L;
        Integer productId = 1;
        CartItemDto cartItemToBeUpdated = CartItemDto.builder()
                .id(new CartItemIdDto(1L,1))
                .quantity(4)
                .isRebate(true)
                .build();
        CartItem updatedCartItem = CartItem.builder()
                .id(new CartItemId(1L,1))
                .quantity(4)
                .isRebate(true)
                .build();
        when(cartItemRepo.findByIdCartIdAndIdProductId(anyLong(),anyInt())).thenReturn(Optional.of(mockCartItem));
        when(cartItemRepo.save(any(CartItem.class))).thenReturn(updatedCartItem);
        when(cartService.updateCart(anyLong())).thenReturn(null);
        when(modelMapper.map(any(CartItemDto.class),eq(CartItem.class))).thenReturn(updatedCartItem);
        when(modelMapper.map(any(CartItem.class),eq(CartItemDto.class))).thenReturn(cartItemToBeUpdated);

        //when
        CartItemDto response = cartItemServiceImp.updateCartItem(cartItemToBeUpdated,cartId,productId);
        //then
        assertNotNull(response);
        assertEquals(cartItemToBeUpdated.getQuantity(),response.getQuantity());
    }

    @Test
    public void cartService_deleteCartItem_returnNull(){
        //given
        Long cartId = 1L;
        Integer productId = 1;
        when(cartItemRepo.findByIdCartIdAndIdProductId(anyLong(),anyInt())).thenReturn(Optional.of(mockCartItem));
        doNothing().when(cartItemRepo).deleteAllByIdCartId(anyLong());
        when(cartService.updateCart(anyLong())).thenReturn(null);

        //when
        assertAll(()->cartItemServiceImp.deleteCartItem(cartId,productId));
        //then
        verify(cartItemRepo, times(1)).delete(mockCartItem);
    }

    @Test
    public void cartService_deleteCartItem_throwsResourceNotFoundException(){
        //given
        Long cartId = 1L;
        Integer productId = 1;
        when(cartItemRepo.findByIdCartIdAndIdProductId(anyLong(),anyInt())).thenReturn(Optional.empty());
        doNothing().when(cartItemRepo).deleteAllByIdCartId(anyLong());
        when(cartService.updateCart(anyLong())).thenReturn(null);

        //when
        assertThrows(ResourceNotFoundException.class, ()->cartItemServiceImp.deleteCartItem(cartId,productId));
        //then
        verify(cartItemRepo, never()).delete(any());
    }

    @Test
    public void cartService_deleteAllCartItems_returnNull(){
        //given
        Long cartId = 1L;
        when(cartRepo.findById(anyLong())).thenReturn(Optional.of(mockCart));
        doNothing().when(cartItemRepo).deleteAllByIdCartId(anyLong());
        when(cartService.updateCart(anyLong())).thenReturn(null);

        //when
        assertAll(()->cartItemServiceImp.deleteAllCartItems(cartId));
        //then
        verify(cartItemRepo, times(1)).deleteAllByIdCartId(cartId);
    }

    @Test
    public void cartService_deleteAllCartItems_throwsResourceNotFoundException(){
        //given
        Long cartId = 1L;
        when(cartRepo.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(cartItemRepo).deleteAllByIdCartId(anyLong());
        when(cartService.updateCart(anyLong())).thenReturn(null);

        //when
        assertThrows(ResourceNotFoundException.class, ()->cartItemServiceImp.deleteAllCartItems(cartId));
        //then
        verify(cartItemRepo, never()).deleteAllByIdCartId(any());
    }
}