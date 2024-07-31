package com.example.biservice.repository.order;

import com.example.biservice.entity.order.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentDetailRepo extends JpaRepository<PaymentDetail, Long> {

    Optional<PaymentDetail> findByRazorpayPaymentId(String razorpayPaymentId);
}
