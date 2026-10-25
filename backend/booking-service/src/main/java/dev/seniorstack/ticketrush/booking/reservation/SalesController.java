package dev.seniorstack.ticketrush.booking.reservation;

import dev.seniorstack.ticketrush.booking.event.Event;
import dev.seniorstack.ticketrush.booking.event.EventNotFoundException;
import dev.seniorstack.ticketrush.booking.event.EventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SalesController {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;

    SalesController(EventRepository eventRepository, ReservationRepository reservationRepository) {
        this.eventRepository = eventRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/events/{eventId}/sales")
    SalesReport sales(@PathVariable Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        long sold = reservationRepository.countSeatsSold(eventId);
        return new SalesReport(eventId, event.getCapacity(), event.getAvailable(), sold,
                Math.max(0, sold - event.getCapacity()));
    }

    record SalesReport(Long eventId, int capacity, int available, long sold, long oversold) {
    }
}
