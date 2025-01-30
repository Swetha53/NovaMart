package com.novamart.payment_service.service;

import com.novamart.payment_service.dto.PaymentRequest;
import com.novamart.payment_service.model.Payment;
import com.novamart.payment_service.respository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void savePayment(PaymentRequest paymentRequest) {
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

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
