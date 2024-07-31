package com.example.biservice.payload.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailDto {

    private Long paymentId;

    private float amount;

    private String paymentMethod;

    private String paymentStatus;

    private LocalDateTime paymentDate;

    private String razorpayPaymentLinkId;

    private String razorpayPaymentLinkReferenceId;

    private String razorpayPaymentLinkStatus;

    private String razorpayPaymentId;

}
