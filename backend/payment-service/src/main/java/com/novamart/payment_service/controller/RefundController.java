package com.novamart.payment_service.controller;

import com.novamart.payment_service.dto.RefundRequest;
import com.novamart.payment_service.model.Refund;
import com.novamart.payment_service.service.RefundService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refund")
@AllArgsConstructor
public class RefundController {
    private final RefundService refundService;

    @PostMapping("/initiate")
    @ResponseStatus(HttpStatus.CREATED)
    public String initiateRefund(@RequestBody RefundRequest refundRequest) {
        refundService.initiateRefund(refundRequest);
        return "Refund Initiated Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Refund getRefundByOrderAndProductId(@RequestParam String orderId, @RequestParam String productId) {
        return refundService.getRefundByOrderAndProductId(orderId, productId);
    }

    @PutMapping("/process")
    @ResponseStatus(HttpStatus.OK)
    public String processRefund(@RequestParam String refundId) {
        refundService.processRefund(refundId);
        return "Refund Processed Successfully";
    }
}
