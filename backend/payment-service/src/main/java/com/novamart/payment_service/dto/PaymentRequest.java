package com.novamart.payment_service.dto;

import java.math.BigDecimal;

public record PaymentRequest(String orderId, String userId, String currencyCode, String paymentMethod,
                             BigDecimal totalAmount, String userEmail) {
}
