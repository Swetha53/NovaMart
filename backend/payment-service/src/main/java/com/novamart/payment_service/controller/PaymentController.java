package com.novamart.payment_service.controller;

import com.novamart.payment_service.dto.PaymentRequest;
import com.novamart.payment_service.model.Payment;
import com.novamart.payment_service.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public String savePayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.savePayment(paymentRequest);
        return "Payment Record Created Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Payment getPaymentByOrderId(@RequestParam String orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }
}
