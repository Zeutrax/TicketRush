CREATE TABLE reservations (
    id         UUID        PRIMARY KEY,
    event_id   BIGINT      NOT NULL REFERENCES events (id),
    quantity   INT         NOT NULL CHECK (quantity BETWEEN 1 AND 6),
    status     VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX reservations_event_id ON reservations (event_id);
