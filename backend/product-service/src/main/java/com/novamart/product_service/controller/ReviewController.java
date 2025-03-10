package com.novamart.product_service.controller;

import com.novamart.product_service.dto.ApiResponse;
import com.novamart.product_service.dto.ReviewRequest;
import com.novamart.product_service.dto.UpdateReviewRequest;
import com.novamart.product_service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addReview(@RequestBody ReviewRequest reviewRequest) {
        return reviewService.addReview(reviewRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getReviews(@RequestParam(required = false) String productId,
                                    @RequestParam(required = false) String userId,
                                    @RequestParam(required = false) String merchantId) {
        if (userId != null) {
            return reviewService.getReviewByUserId(userId);
        } else if (productId != null) {
            return reviewService.getReviewByProductId(productId);
        } else if (merchantId != null) {
            return reviewService.getReviewByMerchantId(merchantId);
        } else {
            return new ApiResponse(401, "Bad Request", null);
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateReview(@RequestBody UpdateReviewRequest updateReviewRequest) {
        return reviewService.updateReview(updateReviewRequest);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteReview(@RequestParam String reviewId) {
        return reviewService.deleteReview(reviewId);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteAllReviews(@RequestParam String userId) {
        return reviewService.deleteAllReviews(userId);
    }
}
