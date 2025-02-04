package com.novamart.product_service.dto;

import java.util.List;

public record ReviewRequest(String userId, String merchantId, String productId, String title, String comment,
                            List<String> imageUrl, int rating) {
}
