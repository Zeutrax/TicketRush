package dev.seniorstack.ticketrush.booking.reservation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CreateReservationRequest(@Min(1) @Max(6) int quantity) {
}
