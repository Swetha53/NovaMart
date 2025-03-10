package com.novamart.payment_service.controller;

import com.novamart.payment_service.dto.ApiResponse;
import com.novamart.payment_service.dto.PaymentRequest;
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
    public ApiResponse savePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.savePayment(paymentRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getPaymentByOrderId(@RequestParam String orderId, @RequestParam String userId) {
        return paymentService.getPaymentByOrderId(orderId, userId);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteAllPayments(@RequestParam String userId) {
        return paymentService.deleteAllPayments(userId);
    }
}
