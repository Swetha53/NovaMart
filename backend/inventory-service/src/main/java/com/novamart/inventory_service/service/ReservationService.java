package com.novamart.inventory_service.service;

import com.novamart.inventory_service.dto.ApiResponse;
import com.novamart.inventory_service.dto.ReservationRequest;
import com.novamart.inventory_service.model.Reservation;
import com.novamart.inventory_service.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

    public ApiResponse getReservationByOrderIdAndProductId(String orderId, String productId) {
        List<Reservation> reservations = Collections.singletonList(
                reservationRepository.findByOrderIdAndProductId(orderId, productId)
        );
        return new ApiResponse(200, "success", reservations);
    }

    public ApiResponse getReservationByReservationId(String reservationId) {
        List<Reservation> reservations = Collections.singletonList(
                reservationRepository.findByReservationId(reservationId)
        );
        return new ApiResponse(200, "success", reservations);
    }

    @Transactional
    public void deleteReservation(String orderId, String productId) {
        reservationRepository.deleteByOrderIdAndProductId(orderId, productId);
    }

    @Transactional
    public void deleteAllReservations() {
        reservationRepository.deleteAll();
    }
}
