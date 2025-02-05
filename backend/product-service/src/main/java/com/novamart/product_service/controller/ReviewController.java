package com.novamart.product_service.controller;

import com.novamart.product_service.dto.ReviewRequest;
import com.novamart.product_service.dto.UpdateReviewRequest;
import com.novamart.product_service.model.Reviews;
import com.novamart.product_service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.addReview(reviewRequest);
        return "Review added successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Reviews> getReviews(@RequestParam(required = false) String productId,
                                    @RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String merchantId) {
        if (userId != null) {
            return reviewService.getReviewByUserId(userId);
        } else if (productId != null) {
            return reviewService.getReviewByProductId(productId);
        } else if (merchantId != null) {
            return reviewService.getReviewByMerchantId(merchantId);
        } else {
            throw new IllegalArgumentException("Invalid request");
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Reviews updateReview(@RequestBody UpdateReviewRequest updateReviewRequest) {
        return reviewService.updateReview(updateReviewRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteReview(@RequestParam String reviewId) {
        reviewService.deleteReview(reviewId);
        return "Review deleted successfully";
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAllReviews(@RequestParam String userId) {
        reviewService.deleteAllReviews(userId);
        return "All reviews deleted";
    }
}
