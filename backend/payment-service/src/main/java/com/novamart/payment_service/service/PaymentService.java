package com.novamart.payment_service.service;

import com.novamart.payment_service.client.UserClient;
import com.novamart.payment_service.dto.PaymentRequest;
import com.novamart.payment_service.model.Payment;
import com.novamart.payment_service.respository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RefundService refundService;
    private final UserClient userClient;

    public void savePayment(PaymentRequest paymentRequest) {
        if (!userClient.authenticateUser(paymentRequest.userId(), "accountType", "CUSTOMER")) {
            throw new RuntimeException("User not authenticated");
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
    }

    public Payment getPaymentByOrderId(String orderId, String userId) {
        if (!userClient.authenticateUser(userId, "role", "VIEW")) {
            throw new RuntimeException("User not authenticated");
        }
        return paymentRepository.findByOrderId(orderId);
    }

    @Transactional
    public void deleteAllPayments(String userId) {
        if (!userClient.authenticateUser(userId, "accountType", "ADMIN")) {
            throw new RuntimeException("User not authenticated");
        }
        refundService.deleteAllRefunds();
        paymentRepository.deleteAll();
    }
}
