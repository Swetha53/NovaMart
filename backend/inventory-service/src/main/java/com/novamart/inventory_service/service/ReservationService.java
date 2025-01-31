package com.novamart.inventory_service.service;

import com.novamart.inventory_service.dto.ReservationRequest;
import com.novamart.inventory_service.model.Reservation;
import com.novamart.inventory_service.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public void createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.randomUUID().toString());
        reservation.setOrderId(reservationRequest.orderId());
        reservation.setUserId(reservationRequest.userId());
        reservation.setProductId(reservationRequest.productId());
        reservation.setInventoryId(reservationRequest.inventoryId());
        reservation.setQuantity(reservationRequest.quantity());
        reservation.setCreatedAt(System.currentTimeMillis());

        reservationRepository.save(reservation);
    }

    public Reservation getReservationByOrderIdAndProductId(String orderId, String productId) {
        return reservationRepository.findByOrderIdAndProductId(orderId, productId);
    }

    public Reservation getReservationByReservationId(String reservationId) {
        return reservationRepository.findByReservationId(reservationId);
    }

    @Transactional
    public void deleteReservation(String orderId, String productId) {
        reservationRepository.deleteByOrderIdAndProductId(orderId, productId);
    }
}
