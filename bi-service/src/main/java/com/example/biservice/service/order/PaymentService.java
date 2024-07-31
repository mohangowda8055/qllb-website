package com.example.biservice.service.order;

import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.payload.order.PaymentDetailDto;
import com.example.biservice.payload.order.PaymentLinkResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentLinkResponse createPayment(Long orderId) throws RazorpayException;

    OrderResponseDto createPaymentDetail(Long OrderId, PaymentDetailDto paymentDetailDto) throws RazorpayException;

    OrderResponseDto createCODPaymentDetail(Long orderId);
}
