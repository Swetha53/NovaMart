package com.novamart.cart_service.dto;

import com.novamart.cart_service.model.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record CartResponse(String userId, BigDecimal totalAmount, String currencyCode, long createdAt, long updatedAt,
                           List<CartItem> cartItemList) {
}
