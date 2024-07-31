package com.example.biservice.service.order;

import com.example.biservice.entity.cart.Cart;
import com.example.biservice.entity.cart.CartItem;
import com.example.biservice.entity.order.*;
import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.order.OrderDto;
import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.payload.user.AddressDto;
import com.example.biservice.repository.cart.CartRepo;
import com.example.biservice.repository.order.OrderRepo;
import com.example.biservice.repository.user.UserRepo;
import com.example.biservice.service.cart.CartItemService;
import com.example.biservice.service.user.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{

    private final ModelMapper modelMapper;

    private final OrderRepo orderRepo;

    private final UserRepo userRepo;

    private final AddressService addressService;

    private final CartRepo cartRepo;

    private final CartItemService cartItemService;


    @Override
    public OrderResponseDto createOrder(OrderDto orderDto, Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot create order - no existing user found with id "+userId));
        Cart cart = this.cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cannot create order - no existing cart found with user id "+userId));
        Order order = this.modelMapper.map(orderDto, Order.class);
        order.setTotal(cart.getTotal());
        order.setDeliveryCost(cart.getDeliveryCost());
        order.setGrandTotal(cart.getTotal() + cart.getDeliveryCost());
        order.setOrderStatus(orderDto.getPaymentMethod().equals("CASH_ON_DELIVERY") ? OrderStatus.PLACED : OrderStatus.PENDING);
        order.setPaymentMethod(orderDto.getPaymentMethod().equals("ONLINE") ? "ONLINE" : "CASH_ON_DELIVERY");
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = this.orderRepo.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItems()){
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(order.getOrderId());
            orderItemId.setProductId(cartItem.getProduct().getProductId());

            OrderItem orderItem = OrderItem.builder()
                    .id(orderItemId)
                    .quantity(cartItem.getQuantity())
                    .isRebate(cartItem.isRebate())
                    .product(cartItem.getProduct())
                    .order(savedOrder)
                    .build();
            orderItems.add(orderItem);
        }

        savedOrder.setOrderItems(orderItems);
        AddressDto addressDto = this.addressService.getAddressByUserAndAddressType(userId, "DELIVERY");

        ShippingAddress shippingAddress = ShippingAddress.builder()
                .orderId(savedOrder.getOrderId())
                .addressLine1(addressDto.getAddressLine1())
                .addressLine2(addressDto.getAddressLine2())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .postalCode(addressDto.getPostalCode())
                .phoneNumber(addressDto.getPhoneNumber())
                .order(savedOrder)
                .build();
        savedOrder.setShippingAddress(shippingAddress);
        Order savedOrder1 = this.orderRepo.save(savedOrder);
        if(orderDto.getPaymentMethod().equals("CASH_ON_DELIVERY")){
            this.cartItemService.deleteAllCartItems(savedOrder.getUser().getUserId());
        }
        return this.modelMapper.map(savedOrder1, OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot find order - no existing order found with id "+orderId));
        return this.modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public PageResponse<OrderResponseDto> findAllOrders(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Order> pageOrders = this.orderRepo.findAll(pageable);
        List<Order> Orders = pageOrders.getContent();
        List<OrderResponseDto> orderDtos = Orders.stream().map((order)->this.modelMapper.map(order, OrderResponseDto.class)).collect(Collectors.toList());
        return PageResponse.<OrderResponseDto>builder()
                .data(orderDtos)
                .pageNumber(pageOrders.getNumber())
                .pageSize(pageOrders.getSize())
                .totalElements(pageOrders.getTotalElements())
                .totalPages(pageOrders.getTotalPages())
                .lastPage(pageOrders.isLast())
                .build();
    }

    @Override
    public PageResponse<OrderResponseDto> findAllOrdersByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Cannot find orders - no existing user found with id "+userId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Order> pageOrders = this.orderRepo.findAllByUser(user,pageable);
        List<Order> Orders = pageOrders.getContent();
        List<OrderResponseDto> orderDtos = Orders.stream().map((order)->this.modelMapper.map(order, OrderResponseDto.class)).collect(Collectors.toList());
        return PageResponse.<OrderResponseDto>builder()
                .data(orderDtos)
                .pageNumber(pageOrders.getNumber())
                .pageSize(pageOrders.getSize())
                .totalElements(pageOrders.getTotalElements())
                .totalPages(pageOrders.getTotalPages())
                .lastPage(pageOrders.isLast())
                .build();
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Long orderId) {
        Optional<Order> optionalOrder = this.orderRepo.findById(orderId);
        if(optionalOrder.isPresent()){
            orderDto.setOrderStatus(orderDto.getOrderStatus().toUpperCase());
            Order order = this.modelMapper.map(orderDto, Order.class);
            order.setUser(optionalOrder.get().getUser());
            order.setOrderItems(optionalOrder.get().getOrderItems());
            Order updatedOrder = this.orderRepo.save(order);
            return this.modelMapper.map(updatedOrder, OrderDto.class);
        }else{
            throw new ResourceNotFoundException("Cannot update order - no existing order found with user id "+orderId);
        }
    }

    @Override
    public void updateOrderPlaced(Long orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot update order - no existing order found with id "+orderId));
        order.setOrderStatus(OrderStatus.PLACED);
        this.orderRepo.save(order);
    }

    @Override
    public void updateOrderFailed(Long orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot update order - no existing order found with id "+orderId));
        order.setOrderStatus(OrderStatus.FAILED);
        this.orderRepo.save(order);
    }

    @Override
    public void updateOrderShipped(Long orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot update order - no existing order found with id "+orderId));
        order.setOrderStatus(OrderStatus.SHIPPED);
        this.orderRepo.save(order);
    }

    @Override
    public void updateOrderOutForDelivery(Long orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot update order - no existing order found with id "+orderId));
        order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
        this.orderRepo.save(order);
    }

    @Override
    public void updateOrderDelivered(Long orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot update order - no existing order found with id "+orderId));
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate((LocalDateTime.now()));
        this.orderRepo.save(order);
    }

}
