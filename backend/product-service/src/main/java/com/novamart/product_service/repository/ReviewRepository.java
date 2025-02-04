package com.novamart.product_service.repository;

import com.novamart.product_service.model.Reviews;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Reviews, String> {
    @Query("{ 'productId' : ?0 }")
    List<Reviews> findByProductId(String productId);

    @Query("{ 'userId' : ?0 }")
    List<Reviews> findByUserId(String userId);

    @Query("{ 'merchantId' : ?0 }")
    List<Reviews> findByMerchantId(String merchantId);

    @Query("{ 'reviewId' : ?0 }")
    Reviews findByReviewId(String reviewId);
}
