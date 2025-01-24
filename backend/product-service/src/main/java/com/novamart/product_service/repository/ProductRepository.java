package com.novamart.product_service.repository;

import com.novamart.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{ 'product_id': ?0 }")
    Product findByProductId(String product_id);
}
