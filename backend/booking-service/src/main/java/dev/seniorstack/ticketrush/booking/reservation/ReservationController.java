package dev.seniorstack.ticketrush.booking.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/events/{eventId}/reservations")
class ReservationController {

    private final ReservationService reservationService;

    ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    ResponseEntity<ReservationResponse> reserve(@PathVariable Long eventId,
                                                @Valid @RequestBody CreateReservationRequest request) {
        Reservation reservation = reservationService.reserve(eventId, request.quantity());
        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(ReservationResponse.from(reservation));
    }
}
