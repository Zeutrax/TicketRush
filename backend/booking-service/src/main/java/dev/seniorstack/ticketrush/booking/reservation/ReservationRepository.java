package dev.seniorstack.ticketrush.booking.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query("select coalesce(sum(r.quantity), 0) from Reservation r where r.eventId = :eventId")
    long countSeatsSold(Long eventId);
}
