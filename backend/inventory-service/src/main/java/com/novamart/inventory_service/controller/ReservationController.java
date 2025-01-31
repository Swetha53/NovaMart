package com.novamart.inventory_service.controller;

import com.novamart.inventory_service.dto.ReservationRequest;
import com.novamart.inventory_service.model.Reservation;
import com.novamart.inventory_service.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createReservation(@RequestBody ReservationRequest reservationRequest) {
        reservationService.createReservation(reservationRequest);
        return "Reservation Created Successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Reservation getReservation(@RequestParam(required = false) String orderId,
                                      @RequestParam(required = false) String productId,
                                      @RequestParam(required = false) String reservationId) {
        if (orderId != null && productId != null) {
            return reservationService.getReservationByOrderIdAndProductId(orderId, productId);
        } else if (reservationId != null) {
            return reservationService.getReservationByReservationId(reservationId);
        } else {
            throw new RuntimeException("Invalid request");
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteReservation(@RequestParam String orderId, @RequestParam String productId) {
        reservationService.deleteReservation(orderId, productId);
        return "Reservation Deleted Successfully";
    }
}
