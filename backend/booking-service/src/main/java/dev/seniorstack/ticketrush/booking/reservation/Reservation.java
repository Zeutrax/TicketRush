package dev.seniorstack.ticketrush.booking.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    private UUID id;

    private Long eventId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Instant createdAt;

    protected Reservation() {
        // for JPA
    }

    public Reservation(Long eventId, int quantity) {
        this.id = UUID.randomUUID();
        this.eventId = eventId;
        this.quantity = quantity;
        this.status = ReservationStatus.HELD;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public int getQuantity() {
        return quantity;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
