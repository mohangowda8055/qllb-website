package com.example.biservice.controller.order;

import com.example.biservice.payload.ApiResponse;
import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.payload.order.PaymentDetailDto;
import com.example.biservice.payload.order.PaymentLinkResponse;
import com.example.biservice.service.order.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}/payments")
    public ResponseEntity<ApiResponse<PaymentLinkResponse>> createPaymentLink(@PathVariable Long orderId) throws RazorpayException {
    PaymentLinkResponse data = this.paymentService.createPayment(orderId);
    ApiResponse<PaymentLinkResponse> apiResponse = ApiResponse.<PaymentLinkResponse>builder()
            .success(true)
            .data(data)
            .status(HttpStatus.CREATED.value())
            .message("Created Payment")
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/orders/{orderId}/paymentdetails")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createPaymentDetail(@PathVariable Long orderId, @RequestBody PaymentDetailDto paymentDetailDto) throws RazorpayException {
        OrderResponseDto data = this.paymentService.createPaymentDetail(orderId, paymentDetailDto);
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.<OrderResponseDto>builder()
                .success(data!=null)
                .data(data)
                .status(data==null ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value())
                .message(data==null ? "Order not captured" : "Order Placed")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/orders/{orderId}/cod/paymentdetails")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createCODPaymentDetail(@PathVariable Long orderId) {
        OrderResponseDto data = this.paymentService.createCODPaymentDetail(orderId);
        ApiResponse<OrderResponseDto> apiResponse = ApiResponse.<OrderResponseDto>builder()
                .success(data!=null)
                .data(data)
                .status(data==null ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value())
                .message(data==null ? "Order not captured" : "Payment Successful")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
