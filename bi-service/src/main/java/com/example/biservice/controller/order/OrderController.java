package com.example.biservice.controller.order;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.order.OrderDto;
import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@PathVariable Long userId, @RequestBody OrderDto orderDto){
        OrderResponseDto data = this.orderService.createOrder(orderDto, userId);
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.<OrderResponseDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.CREATED.value())
                .message("Created Order")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(@PathVariable Long orderId){
        OrderResponseDto data = this.orderService.getOrder(orderId);
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.<OrderResponseDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.FOUND.value())
                .message("Found Order")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<PageResponse<OrderResponseDto>> findAllOrdersByUser(@PathVariable Long userId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                          @RequestParam(value = "sortBy", defaultValue = "orderDate", required = false) String sortBy) {
        PageResponse<OrderResponseDto> pageResponse = this.orderService.findAllOrdersByUser(userId, pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Orders");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<PageResponse<OrderResponseDto>> findAllOrders(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                              @RequestParam(value = "sortBy", defaultValue = "orderDate", required = false) String sortBy) {
        PageResponse<OrderResponseDto> pageResponse = this.orderService.findAllOrders(pageNumber, pageSize, sortBy);
        pageResponse.setSuccess(true);
        pageResponse.setStatus(HttpStatus.FOUND.value());
        pageResponse.setMessage("Found all Orders");
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto){
        OrderDto data = this.orderService.updateOrder(orderDto,orderId);
        ApiResponse<OrderDto> apiResponse = ApiResponse.<OrderDto>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK.value())
                .message("Updated Order")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PatchMapping("/orders/{orderId}/placed")
    public ResponseEntity<ApiResponse<Void>> updateOrderPlaced(@PathVariable Long orderId){
        this.orderService.updateOrderPlaced(orderId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Updated Order Placed")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PatchMapping("/orders/{orderId}/failed")
    public ResponseEntity<ApiResponse<Void>> updateOrderFailed(@PathVariable Long orderId){
        this.orderService.updateOrderFailed(orderId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Updated Order Failed")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/orders/{orderId}/shipped")
    public ResponseEntity<ApiResponse<Void>> updateOrderShipped(@PathVariable Long orderId){
        this.orderService.updateOrderShipped(orderId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Updated Order Shipped")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/orders/{orderId}/outfordelivery")
    public ResponseEntity<ApiResponse<Void>> updateOrderOutForDelivery(@PathVariable Long orderId){
        this.orderService.updateOrderOutForDelivery(orderId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Updated Order Out For Delivery")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/orders/{orderId}/delivery")
    public ResponseEntity<ApiResponse<Void>> updateOrderDelivered(@PathVariable Long orderId){
        this.orderService.updateOrderDelivered(orderId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .data(null)
                .status(HttpStatus.OK.value())
                .message("Updated Order Delivered")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
