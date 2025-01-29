package com.novamart.order_service.repository;

import com.novamart.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT oi FROM Order oi WHERE oi.userId = ?1")
    List<Order> findByUserId(String userId);
}
