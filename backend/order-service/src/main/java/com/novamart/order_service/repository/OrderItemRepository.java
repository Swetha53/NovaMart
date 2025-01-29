package com.novamart.order_service.repository;

import com.novamart.order_service.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.orderId = ?1")
    List<OrderItem> findByOrderId(String orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.merchantId = ?1")
    List<OrderItem> findByMerchantId(String merchantId);
}
