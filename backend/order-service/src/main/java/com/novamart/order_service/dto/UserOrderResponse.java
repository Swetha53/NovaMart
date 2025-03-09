package com.novamart.order_service.dto;

import com.novamart.order_service.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record UserOrderResponse(String orderId, String userId, String customerName, String orderStatus, BigDecimal totalAmount,
                                String currencyCode, long createdAt, List<OrderItem> orderItemList) {
}
