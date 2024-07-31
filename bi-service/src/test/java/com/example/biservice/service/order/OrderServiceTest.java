package com.example.biservice.service.order;

import com.example.biservice.entity.cart.Cart;
import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.cart.CartItemId;
import com.example.biservice.entity.order.Order;
import com.example.biservice.entity.order.OrderStatus;
import com.example.biservice.entity.twowheeler.TwoVBattery;
import com.example.biservice.entity.user.User;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.order.OrderDto;
import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.repository.cart.CartRepo;
import com.example.biservice.repository.order.OrderRepo;
import com.example.biservice.repository.user.UserRepo;
import com.example.biservice.service.cart.CartItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderServiceImp orderServiceImp;

    private Order mockOrder;

    private OrderDto mockOrderDto;

    private User mockUser;

    private Cart mockCart;

    private CartItem mockCartItem;

    private TwoVBattery mockProduct;

    @BeforeEach
    void setUp() {
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
        mockUser = User.builder()
                .userId(1L)
                .build();
        mockCartItem = CartItem.builder()
                .id(new CartItemId(1L,1))
                .quantity(2)
                .isRebate(true)
                .cart(mockCart)
                .product(mockProduct)
                .build();
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(mockCartItem);
        mockCart = Cart.builder()
                .cartId(1L)
                .user(mockUser)
                .cartItems(cartItemList)
                .build();
        mockOrderDto = OrderDto.builder()
                .orderId(1L)
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .orderStatus("Ordered")
                .paymentMethod("COD")
                .build();
        mockOrder = Order.builder()
                .orderId(1L)
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PLACED)
                .paymentMethod("COD")
                .build();
        when(modelMapper.map(Mockito.any(OrderDto.class), Mockito.eq(Order.class))).thenReturn(mockOrder);
        when(modelMapper.map(Mockito.any(Order.class), Mockito.eq(OrderDto.class))).thenReturn(mockOrderDto);
    }

    @AfterEach
    void tearDown() {
        mockOrder = null;
        mockOrderDto = null;
        mockProduct = null;
        mockUser = null;
        mockCart = null;
        mockCartItem = null;
    }

    @Test
    public void orderService_updateOrder_returnOrder(){
        //given
        Long orderId = 1L;
        OrderDto orderToBeUpdated = OrderDto.builder()
                .orderId(1L)
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .orderStatus("PLACED")
                .paymentMethod("Online")
                .build();
        Order updatedOrder = Order.builder()
                .orderId(1L)
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PLACED)
                .paymentMethod("Online")
                .build();
        when(modelMapper.map(Mockito.any(OrderDto.class), Mockito.eq(Order.class))).thenReturn(updatedOrder);
        when(modelMapper.map(Mockito.any(Order.class), Mockito.eq(OrderDto.class))).thenReturn(orderToBeUpdated);
        when(orderRepo.findById(anyLong())).thenReturn(Optional.of(mockOrder));
        when(orderRepo.save(updatedOrder)).thenReturn(updatedOrder);

        //when
        OrderDto response = orderServiceImp.updateOrder(orderToBeUpdated,orderId);
        //then
        assertNotNull(response);
        assertEquals(orderToBeUpdated.getOrderStatus(),response.getOrderStatus());
    }
}