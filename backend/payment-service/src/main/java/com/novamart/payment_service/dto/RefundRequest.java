package com.novamart.payment_service.dto;

import java.math.BigDecimal;

public record RefundRequest(String paymentId, String userId, String orderId, String productId, String currencyCode,
                            String refundReason, BigDecimal totalAmount, String userEmail) {
}
