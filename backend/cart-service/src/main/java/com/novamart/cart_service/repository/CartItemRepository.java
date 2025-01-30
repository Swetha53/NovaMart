package com.novamart.cart_service.repository;

import com.novamart.cart_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
   @Query("SELECT oi FROM CartItem oi WHERE oi.userId = ?1")
   List<CartItem> findByUserId(String userId);

   @Modifying
   @Transactional
   @Query("DELETE FROM CartItem oi WHERE oi.userId = ?1")
   void deleteByUserId(String userId);

   @Modifying
   @Transactional
   @Query("DELETE FROM CartItem oi WHERE oi.userId = ?1 AND oi.productId = ?2")
   void deleteByUserIdAndProductId(String userId, String productId);
}
