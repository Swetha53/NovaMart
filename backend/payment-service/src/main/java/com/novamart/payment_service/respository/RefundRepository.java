package com.novamart.payment_service.respository;

import com.novamart.payment_service.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefundRepository extends JpaRepository<Refund, String> {
    @Query("SELECT r FROM Refund r WHERE r.orderId = ?1 AND r.productId = ?2")
    Refund findByOrderIdAndProductId(String orderId, String productId);

    @Query("SELECT r FROM Refund r WHERE r.refundId = ?1")
    Refund findByRefundId(String refundId);
}
