package com.novamart.product_service.service;

import com.novamart.product_service.client.UserClient;
import com.novamart.product_service.dto.ReviewRequest;
import com.novamart.product_service.dto.UpdateReviewRequest;
import com.novamart.product_service.model.Reviews;
import com.novamart.product_service.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserClient userClient;

    public void addReview(ReviewRequest reviewRequest) {
        if (!userClient.authenticateUser(reviewRequest.userId(), "accountType", "CUSTOMER")) {
            throw new RuntimeException("User not authenticated");
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
    }

    public List<Reviews> getReviewByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Reviews> getReviewByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Reviews> getReviewByMerchantId(String merchantId) {
        return reviewRepository.findByMerchantId(merchantId);
    }

    public Reviews updateReview(UpdateReviewRequest updateReviewRequest) {
        Reviews review = reviewRepository.findByReviewId(updateReviewRequest.reviewId());
        if (!userClient.authenticateUser(review.getUserId(), "accountType", "CUSTOMER")) {
            throw new RuntimeException("User not authenticated");
        }
        review.setTitle(updateReviewRequest.title());
        review.setComment(updateReviewRequest.comment());
        review.setRating(updateReviewRequest.rating());
        review.setImageUrl(updateReviewRequest.imageUrl());
        reviewRepository.save(review);
        return review;
    }

    public void deleteReview(String reviewId) {
        Reviews review = reviewRepository.findByReviewId(reviewId);
        if (!userClient.authenticateUser(review.getUserId(), "accountType", "CUSTOMER")) {
            throw new RuntimeException("User not authenticated");
        }
        reviewRepository.delete(review);
    }

    public void deleteAllReviews(String userId) {
        if (!userClient.authenticateUser(userId, "accountType", "ADMIN")) {
            throw new RuntimeException("User not authenticated");
        }
        reviewRepository.deleteAll();
    }
}
