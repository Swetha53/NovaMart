package com.novamart.payment_service.controller;

import com.novamart.payment_service.dto.ApiResponse;
import com.novamart.payment_service.dto.RefundRequest;
import com.novamart.payment_service.service.RefundService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refund")
@AllArgsConstructor
public class RefundController {
    private final RefundService refundService;

    @PostMapping("/initiate")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse initiateRefund(@RequestBody RefundRequest refundRequest) {
        return refundService.initiateRefund(refundRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRefundByOrderAndProductId(@RequestParam String orderId, @RequestParam String productId) {
        return refundService.getRefundByOrderAndProductId(orderId, productId);
    }

    @PutMapping("/process")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse processRefund(@RequestParam String refundId, @RequestParam String merchantId, @RequestParam String userEmail) {
        return refundService.processRefund(refundId, merchantId, userEmail);
    }
}
