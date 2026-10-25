package dev.seniorstack.ticketrush.booking.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int capacity;

    private int available;

    protected Event() {
        // for JPA
    }

    /**
     * Takes seats out of the pool. Reads fine, but under concurrent requests two
     * transactions can both see the same "available" value and both succeed.
     */
    public void reserveSeats(int quantity) {
        if (available < quantity) {
            throw new SoldOutException(id, quantity, available);
        }
        available -= quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailable() {
        return available;
    }
}
