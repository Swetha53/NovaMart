package com.novamart.order_service.dto;


import com.novamart.order_service.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(String userId, BigDecimal totalAmount, String currencyCode, List<OrderItem> orderItemList) {
}
