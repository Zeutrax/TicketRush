package dev.seniorstack.ticketrush.booking.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class EventNotFoundException extends ErrorResponseException {

    public EventNotFoundException(Long eventId) {
        super(HttpStatus.NOT_FOUND, problem(eventId), null);
    }

    private static ProblemDetail problem(Long eventId) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Event " + eventId + " does not exist.");
        problem.setType(URI.create("/problems/event-not-found"));
        problem.setTitle("Event not found");
        problem.setProperty("eventId", eventId);
        return problem;
    }
}
