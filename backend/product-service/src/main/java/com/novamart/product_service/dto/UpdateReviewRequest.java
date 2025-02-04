package com.novamart.product_service.dto;

import java.util.List;

public record UpdateReviewRequest(String reviewId, String title, String comment, int rating, List<String> imageUrl) {
}
