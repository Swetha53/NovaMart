package com.novamart.product_service.service;

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

    public void addReview(ReviewRequest reviewRequest) {
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
        log.info("add review");
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
        review.setTitle(updateReviewRequest.title());
        review.setComment(updateReviewRequest.comment());
        review.setRating(updateReviewRequest.rating());
        review.setImageUrl(updateReviewRequest.imageUrl());
        reviewRepository.save(review);
        log.info("update review");
        return review;
    }

    public void deleteReview(String reviewId) {
        Reviews review = reviewRepository.findByReviewId(reviewId);
        reviewRepository.delete(review);
        log.info("delete review");
    }

    public void deleteAllReviews() {
        reviewRepository.deleteAll();
    }
}
