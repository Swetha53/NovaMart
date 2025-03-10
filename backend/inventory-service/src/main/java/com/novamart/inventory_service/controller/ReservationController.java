package com.novamart.inventory_service.controller;

import com.novamart.inventory_service.dto.ApiResponse;
import com.novamart.inventory_service.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getReservation(@RequestParam(required = false) String orderId,
                                      @RequestParam(required = false) String productId,
                                      @RequestParam(required = false) String reservationId) {
        if (orderId != null && productId != null) {
            return reservationService.getReservationByOrderIdAndProductId(orderId, productId);
        } else if (reservationId != null) {
            return reservationService.getReservationByReservationId(reservationId);
        } else {
            return new ApiResponse(401, "Bad Request", null);
        }
    }
}
