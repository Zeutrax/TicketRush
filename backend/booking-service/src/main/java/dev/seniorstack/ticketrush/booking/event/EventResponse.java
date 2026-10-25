package dev.seniorstack.ticketrush.booking.event;

public record EventResponse(Long id, String name, int capacity, int available) {

    static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getName(), event.getCapacity(), event.getAvailable());
    }
}
