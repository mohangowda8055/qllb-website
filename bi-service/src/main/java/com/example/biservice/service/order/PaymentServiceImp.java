package com.example.biservice.service.order;

import com.example.biservice.entity.order.Order;
import com.example.biservice.entity.order.OrderStatus;
import com.example.biservice.entity.order.PaymentDetail;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.payload.order.OrderResponseDto;
import com.example.biservice.payload.order.PaymentDetailDto;
import com.example.biservice.payload.order.PaymentLinkResponse;
import com.example.biservice.repository.order.OrderRepo;
import com.example.biservice.repository.order.PaymentDetailRepo;
import com.example.biservice.service.cart.CartItemService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService{

    @Value("${razorpay.api.key}")
    public String apiKey;

    @Value("${razorpay.api.secret}")
    public String apiSecret;

    private final OrderRepo orderRepo;

    private final PaymentDetailRepo paymentDetailRepo;

    private final CartItemService cartItemService;

    private final ModelMapper modelMapper;

    public PaymentServiceImp(OrderRepo orderRepo, PaymentDetailRepo paymentDetailRepo, CartItemService cartItemService, ModelMapper modelMapper) {
        this.orderRepo = orderRepo;
        this.paymentDetailRepo = paymentDetailRepo;
        this.cartItemService = cartItemService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentLinkResponse createPayment(Long orderId) throws RazorpayException {
        Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Cannot create payment - no existing order found with id " + orderId));
        double amount = order.getGrandTotal();
        int amountInPaise = (int) Math.round(amount * 100);
        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amountInPaise);
            paymentLinkRequest.put("currency", "INR");
            JSONObject customer = new JSONObject();
            customer.put("name",order.getUser().getFirstName());
            customer.put("contact","+91"+order.getUser().getPhoneNumber());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);
            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            JSONObject options = new JSONObject();
            JSONObject checkout = new JSONObject();
            JSONObject theme = new JSONObject();
            JSONObject prefill = new JSONObject();
            theme.put("hide_topbar",true);
            prefill.put("name",order.getUser().getFirstName());
            prefill.put("contact",order.getUser().getPhoneNumber());
            prefill.put("email",order.getUser().getEmail());
            checkout.put("name","QLLB");
            checkout.put("prefill",prefill);
            checkout.put("theme",theme);
            options.put("checkout",checkout);
            paymentLinkRequest.put("options",options);
            paymentLinkRequest.put("callback_url", "https://dbkrdzjjbee2b.cloudfront.net/payment/" + orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = paymentLink.get("id");
            String paymentLinkUrl = paymentLink.get("short_url");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_id(paymentLinkId);
            response.setPayment_link_url(paymentLinkUrl);

            return response;
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

    @Override
    public OrderResponseDto createPaymentDetail(Long orderId, PaymentDetailDto paymentDetailDto) throws RazorpayException {
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot create payment - no existing order found with id "+orderId));
        Optional<PaymentDetail> paymentDetailOptional = this.paymentDetailRepo.findByRazorpayPaymentId(paymentDetailDto.getRazorpayPaymentId());
        if(paymentDetailOptional.isEmpty()){
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
            try {
                Payment payment = razorpayClient.payments.fetch(paymentDetailDto.getRazorpayPaymentId());
                if(payment.get("status").equals("captured")){
                    PaymentDetail paymentDetail = PaymentDetail.builder()
                            .amount(order.getGrandTotal())
                            .paymentMethod("ONLINE")
                            .paymentStatus("SUCCESS")
                            .paymentDate(LocalDateTime.now())
                            .razorpayPaymentId(paymentDetailDto.getRazorpayPaymentId())
                            .razorpayPaymentLinkId(paymentDetailDto.getRazorpayPaymentLinkId())
                            .razorpayPaymentLinkReferenceId(paymentDetailDto.getRazorpayPaymentLinkReferenceId())
                            .razorpayPaymentLinkStatus(paymentDetailDto.getRazorpayPaymentLinkStatus())
                            .order(order)
                            .user(order.getUser())
                            .build();
                    order.setOrderStatus(OrderStatus.PLACED);
                    order.setPaymentDetail(paymentDetail);
                    Order savedOrder = this.orderRepo.save(order);
                    this.cartItemService.deleteAllCartItems(order.getUser().getUserId());
                    return this.modelMapper.map(savedOrder, OrderResponseDto.class);
                }
                return null;
            }catch (Exception e){
                throw new RazorpayException(e.getMessage());
            }
        }else {
            return this.modelMapper.map(order, OrderResponseDto.class);
        }
    }

    @Override
    public OrderResponseDto createCODPaymentDetail(Long orderId){
        Order order = this.orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Cannot create payment - no existing order found with id "+orderId));
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .amount(order.getGrandTotal())
                .paymentMethod("CASH_ON_DELIVERY")
                .paymentDate(LocalDateTime.now())
                .paymentStatus("SUCCESS")
                .order(order)
                .user(order.getUser())
                .build();
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPaymentDetail(paymentDetail);
        Order savedOrder = this.orderRepo.save(order);
        return this.modelMapper.map(savedOrder, OrderResponseDto.class);
    }
}
