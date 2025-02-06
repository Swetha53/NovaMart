package com.novamart.cart_service.dto;

import java.util.List;

public record Reviews(String reviewId, String userId, String merchantId, String productId,
                      String title, String comment, List<String> imageUrl, int rating) {
}
