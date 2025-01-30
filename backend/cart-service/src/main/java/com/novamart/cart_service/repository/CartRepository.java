package com.novamart.cart_service.repository;

import com.novamart.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepository extends JpaRepository<Cart, String> {
    @Query("SELECT oi FROM Cart oi WHERE oi.userId = ?1")
    Cart findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart oi WHERE oi.userId = ?1")
    void deleteByUserId(String userId);
}
