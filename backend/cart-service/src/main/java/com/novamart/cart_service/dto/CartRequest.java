package com.novamart.cart_service.dto;

import java.math.BigDecimal;

public record CartRequest(String userId, String productId, int quantity, BigDecimal unitPrice, String currencyCode) {
}
