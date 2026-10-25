package dev.seniorstack.ticketrush.booking.reservation;

import dev.seniorstack.ticketrush.booking.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class ReservationControllerTest {

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    long eventId;

    @BeforeEach
    void createSmallEvent() {
        eventId = jdbc.sql("INSERT INTO events (name, capacity, available) VALUES ('Test gig', 3, 3) RETURNING id")
                .query(Long.class)
                .single();
    }

    @Test
    void reservesSeatsAndReducesAvailability() {
        assertThat(reserve(2))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .isLenientlyEqualTo("""
                        { "eventId": %d, "quantity": 2, "status": "HELD" }
                        """.formatted(eventId));

        assertThat(mvc.get().uri("/events/{id}/sales", eventId))
                .bodyJson()
                .isLenientlyEqualTo("""
                        { "capacity": 3, "available": 1, "sold": 2, "oversold": 0 }
                        """);
    }

    @Test
    void refusesWhenNotEnoughSeatsAreLeft() {
        assertThat(reserve(4))
                .hasStatus(HttpStatus.CONFLICT)
                .bodyJson()
                .isLenientlyEqualTo("""
                        { "title": "Not enough tickets left", "detail": "Requested 4, available 3." }
                        """);
    }

    @Test
    void rejectsInvalidQuantity() {
        assertThat(reserve(7)).hasStatus(HttpStatus.BAD_REQUEST);
    }

    private MockMvcTester.MockMvcRequestBuilder reserve(int quantity) {
        return mvc.post().uri("/events/{id}/reservations", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "quantity": %d }
                        """.formatted(quantity));
    }
}
