package dev.seniorstack.ticketrush.booking.event;

import dev.seniorstack.ticketrush.booking.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class EventControllerTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void returnsTheSeededDemoEvent() {
        assertThat(mvc.get().uri("/events/1"))
                .hasStatusOk()
                .bodyJson()
                .isLenientlyEqualTo("""
                        { "id": 1, "name": "Senior Stack Live", "capacity": 100, "available": 100 }
                        """);
    }

    @Test
    void unknownEventIsNotFound() {
        assertThat(mvc.get().uri("/events/999"))
                .hasStatus(HttpStatus.NOT_FOUND);
    }
}
