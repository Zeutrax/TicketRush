package dev.seniorstack.ticketrush.booking.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
class EventController {

    private final EventRepository eventRepository;

    EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/{id}")
    EventResponse getEvent(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(EventResponse::from)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}
