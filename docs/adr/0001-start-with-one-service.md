# ADR 0001: Start with one service, split later

- Status: accepted
- Date: 2026-10-25

## Context

TicketRush will end up as several services (events, booking, payments, tickets). Starting with microservices on day one hides the reason they exist: viewers see the complexity before they see the problem it solves.

## Decision

Start with a single `booking-service` that owns events and reservations. Split it in video 3, once the reservation logic is correct and we can show what each new service is responsible for.

## Consequences

- Videos 1 and 2 stay focused on one hard problem: never selling more seats than exist.
- The `events` table lives in `booking-service` for now and moves to `event-service` in video 3, where `booking-service` keeps only an `inventory` row per event.
- Package by feature (`event`, `reservation`) from the start, so the split is mostly a move, not a rewrite.
