package com.novamart.order_service.repository;

import com.novamart.order_service.model.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, String> {
    @Query("SELECT oi FROM StatusHistory oi WHERE oi.orderId = ?1")
    List<StatusHistory> findByOrderId(String orderId);
}
