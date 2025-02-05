package com.novamart.product_service.repository;

import com.novamart.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{ 'productId': ?0 }")
    Product findByProductId(String productId);

    @Query("{ 'merchantId': ?0 }")
    List<Product> findByMerchantId(String merchantId);
}
