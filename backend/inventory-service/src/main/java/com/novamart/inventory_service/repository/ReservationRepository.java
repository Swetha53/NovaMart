package com.novamart.inventory_service.repository;

import com.novamart.inventory_service.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    @Query("SELECT r FROM Reservation r WHERE r.orderId = ?1 AND r.productId = ?2")
    Reservation findByOrderIdAndProductId(String orderId, String productId);

    @Query("SELECT r FROM Reservation r WHERE r.reservationId = ?1")
    Reservation findByReservationId(String reservationId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.orderId = ?1 AND r.productId = ?2")
    void deleteByOrderIdAndProductId(String orderId, String productId);
}
