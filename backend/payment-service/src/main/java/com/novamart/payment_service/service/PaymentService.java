package com.novamart.payment_service.service;

import com.novamart.payment_service.client.UserClient;
import com.novamart.payment_service.dto.ApiResponse;
import com.novamart.payment_service.dto.PaymentRequest;
import com.novamart.payment_service.model.Payment;
import com.novamart.payment_service.respository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RefundService refundService;
    private final UserClient userClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApiResponse savePayment(PaymentRequest paymentRequest) {
        ApiResponse user = userClient.authenticateUser(paymentRequest.userId(), "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setOrderId(paymentRequest.orderId());
        payment.setUserId(paymentRequest.userId());
        payment.setStatus("COMPLETED");
        payment.setCurrencyCode(paymentRequest.currencyCode());
        payment.setTotalAmount(paymentRequest.totalAmount());
        payment.setPaymentMethod(paymentRequest.paymentMethod());
        payment.setCreatedAt(System.currentTimeMillis());
        payment.setUpdatedAt(System.currentTimeMillis());

        paymentRepository.save(payment);

        try {
            kafkaTemplate.send("payment-completed", paymentRequest.userEmail());
        } catch (Exception e) {
            log.error("Error publishing payment completion: {}", e.getMessage());
        }

        return new ApiResponse(200, "Payment Record Created Successfully", null);
    }

    public ApiResponse getPaymentByOrderId(String orderId, String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "role", "VIEW");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Payment payment = paymentRepository.findByOrderId(orderId);
        if (payment==null) {
            return new ApiResponse(404, "Payment not found", null);
        }
        List<Payment> paymentList = Collections.singletonList(payment);
        return new ApiResponse(200, "Success", paymentList);
    }

    @Transactional
    public ApiResponse deleteAllPayments(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "ADMIN");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        refundService.deleteAllRefunds();
        paymentRepository.deleteAll();
        return new ApiResponse(200, "All Payment and Refund Records Deleted Successfully", null);
    }
}
