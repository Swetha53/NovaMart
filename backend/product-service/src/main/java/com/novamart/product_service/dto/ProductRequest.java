package com.novamart.product_service.dto;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;

public record ProductRequest(String product_id, String name, String description, BigDecimal price, String currency_code,
                             Object categories, Object reviews, Timestamp created_at, Timestamp updated_at,
                             String status, Array attributes) {
}
