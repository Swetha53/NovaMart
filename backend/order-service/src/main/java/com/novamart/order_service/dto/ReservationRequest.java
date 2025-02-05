package com.novamart.order_service.dto;

public record ReservationRequest(String orderId, String userId, String productId, long quantity) {
}
