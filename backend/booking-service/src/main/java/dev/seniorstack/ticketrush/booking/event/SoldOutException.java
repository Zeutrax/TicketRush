package dev.seniorstack.ticketrush.booking.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class SoldOutException extends ErrorResponseException {

    public SoldOutException(Long eventId, int requested, int available) {
        super(HttpStatus.CONFLICT, problem(eventId, requested, available), null);
    }

    private static ProblemDetail problem(Long eventId, int requested, int available) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Requested " + requested + ", available " + available + ".");
        problem.setType(URI.create("/problems/sold-out"));
        problem.setTitle("Not enough tickets left");
        problem.setProperty("eventId", eventId);
        return problem;
    }
}
