package dev.seniorstack.ticketrush.booking.reservation;

import java.time.Instant;
import java.util.UUID;

public record ReservationResponse(UUID id, Long eventId, int quantity, ReservationStatus status, Instant createdAt) {

    static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getEventId(),
                reservation.getQuantity(),
                reservation.getStatus(),
                reservation.getCreatedAt());
    }
}
