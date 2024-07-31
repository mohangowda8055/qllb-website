package com.example.biservice.controller.order;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.order.OrderDto;
import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.order.OrderService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

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
    private OrderService orderService;

    private OrderResponseDto mockOrderDto;

    @BeforeEach
    void setUp() {
        mockOrderDto = OrderResponseDto.builder()
                .orderId(1L)
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .paymentMethod("COD")
                .build();
    }

    @AfterEach
    void tearDown() {
        mockOrderDto = null;
    }

    @Test
    public void orderController_createOrder_returnCreated() throws Exception{
        //given
        OrderDto inputOrder = OrderDto.builder()
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .paymentMethod("COD")
                .build();
        when(orderService.createOrder(any(OrderDto.class),anyLong())).thenReturn(mockOrderDto);

        //when&then
        mockMvc.perform(post("/api/v1/order/users/1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputOrder)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.total").value(inputOrder.getTotal()));
    }

    @Test
    public void orderController_getOrderById_returnOrder() throws Exception{
        //given
        when(orderService.getOrder(anyLong())).thenReturn(mockOrderDto);

        //when&then
        mockMvc.perform(get("/api/v1/order/orders/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.total").value(mockOrderDto.getTotal()));

    }

    @Test
    public void orderController_findAllOrdersByUser_returnOrderList() throws Exception{
        //given
        List<OrderResponseDto> orders = List.of(
                OrderResponseDto.builder()
                        .orderId(1L)
                        .total(2000)
                        .deliveryCost(50)
                        .grandTotal(2050)
                        .orderDate(LocalDateTime.now())
                        .paymentMethod("COD")
                        .build(),
                OrderResponseDto.builder()
                        .orderId(2L)
                        .total(3000)
                        .deliveryCost(50)
                        .grandTotal(3050)
                        .orderDate(LocalDateTime.now())
                        .paymentMethod("COD")
                        .build()
        );
        PageResponse<OrderResponseDto> pageResponse = PageResponse.<OrderResponseDto>builder()
                .data(orders)
                .pageNumber(0)
                .build();
        when(orderService.findAllOrdersByUser(anyLong(),anyInt(),anyInt(),anyString())).thenReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/order/users/1/orders")
                        .param("pageNumber","0")
                        .param("pageSize","4")
                        .param("sortBy","orderDate"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void orderController_updateOrder_returnOrder() throws Exception{
        //given
        OrderDto orderToBeUpdated = OrderDto.builder()
                .orderId(1L)
                .total(2000)
                .deliveryCost(50)
                .grandTotal(2050)
                .orderDate(LocalDateTime.now())
                .paymentMethod("Online")
                .build();
        when(orderService.updateOrder(any(OrderDto.class),anyLong())).thenReturn(orderToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/order/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.paymentMethod").value(orderToBeUpdated.getPaymentMethod()));
    }
}