package dev.seniorstack.ticketrush.booking.reservation;

import dev.seniorstack.ticketrush.booking.event.Event;
import dev.seniorstack.ticketrush.booking.event.EventNotFoundException;
import dev.seniorstack.ticketrush.booking.event.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(EventRepository eventRepository, ReservationRepository reservationRepository) {
        this.eventRepository = eventRepository;
        this.reservationRepository = reservationRepository;
    }

    // Naive version: check-then-act. Fixed in video 2.
    @Transactional
    public Reservation reserve(Long eventId, int quantity) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        event.reserveSeats(quantity);

        return reservationRepository.save(new Reservation(eventId, quantity));
    }
}
