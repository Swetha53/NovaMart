package com.novamart.user_service.repository;

import com.novamart.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository  extends MongoRepository<User, String> {
    @Query("{ 'email': ?0 }")
    User findByEmail(String email);

    @Query("{ 'userId': ?0 }")
    User findByUserId(String userId);
}
