package com.novamart.product_service.service;

import com.novamart.product_service.client.UserClient;
import com.novamart.product_service.dto.ApiResponse;
import com.novamart.product_service.dto.ReviewRequest;
import com.novamart.product_service.dto.UpdateReviewRequest;
import com.novamart.product_service.model.Reviews;
import com.novamart.product_service.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserClient userClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ApiResponse addReview(ReviewRequest reviewRequest) {
        ApiResponse user = userClient.authenticateUser(reviewRequest.userId(), "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        Reviews review = Reviews.builder()
                        .reviewId(UUID.randomUUID().toString())
                        .userId(reviewRequest.userId())
                        .merchantId(reviewRequest.merchantId())
                        .productId(reviewRequest.productId())
                        .title(reviewRequest.title())
                        .comment(reviewRequest.comment())
                        .imageUrl(reviewRequest.imageUrl())
                        .rating(reviewRequest.rating())
                        .build();
        reviewRepository.save(review);

        try {
            kafkaTemplate.send("review-added", reviewRequest.userEmail());
        } catch (Exception e) {
            log.error("Error publishing review added: {}", e.getMessage());
        }

        return new ApiResponse(200, "Review added successfully", null);
    }

    public ApiResponse getReviewByProductId(String productId) {
        return new ApiResponse(200, "All reviews of product received successfully", reviewRepository.findByProductId(productId));
    }

    public ApiResponse getReviewByUserId(String userId) {
        return new ApiResponse(200, "All reviews by the user received successfully", reviewRepository.findByUserId(userId));
    }

    public ApiResponse getReviewByMerchantId(String merchantId) {
        return new ApiResponse(200, "All reviews for the merchant received successfully", reviewRepository.findByMerchantId(merchantId));
    }

    public ApiResponse updateReview(UpdateReviewRequest updateReviewRequest) {
        Reviews review = reviewRepository.findByReviewId(updateReviewRequest.reviewId());
        ApiResponse user = userClient.authenticateUser(review.getUserId(), "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        review.setTitle(updateReviewRequest.title());
        review.setComment(updateReviewRequest.comment());
        review.setRating(updateReviewRequest.rating());
        review.setImageUrl(updateReviewRequest.imageUrl());
        reviewRepository.save(review);
        List<Reviews> reviews = Collections.singletonList(review);
        return new ApiResponse(200, "Review updated successfully", reviews);
    }

    public ApiResponse deleteReview(String reviewId) {
        Reviews review = reviewRepository.findByReviewId(reviewId);
        ApiResponse user = userClient.authenticateUser(review.getUserId(), "accountType", "CUSTOMER");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        reviewRepository.delete(review);

        return new ApiResponse(200, "Review deleted successfully", null);
    }

    public ApiResponse deleteAllReviews(String userId) {
        ApiResponse user = userClient.authenticateUser(userId, "accountType", "ADMIN");
        if (user == null || user.status() != 200) {
            return new ApiResponse(401, "User not authenticated", null);
        }
        reviewRepository.deleteAll();

        return new ApiResponse(200, "All reviews deleted successfully", null);
    }
}
