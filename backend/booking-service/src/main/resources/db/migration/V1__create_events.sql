CREATE TABLE events (
    id        BIGSERIAL    PRIMARY KEY,
    name      VARCHAR(200) NOT NULL,
    capacity  INT          NOT NULL CHECK (capacity > 0),
    available INT          NOT NULL
);

-- Demo event for the load test: a 100-seat concert.
INSERT INTO events (name, capacity, available) VALUES ('Senior Stack Live', 100, 100);
