# TicketRush

A ticketing system that never sells more tickets than there are seats, even when 10,000 people hit "buy" at the same second.

Built step by step on the **Senior Stack** YouTube channel: Spring Boot microservices on the backend, Jetpack Compose on Android.

## Repository layout

| Folder | What lives there |
| --- | --- |
| `backend/` | Spring Boot services (Gradle multi-project, open this folder's parent in IntelliJ) |
| `android/` | Android app (open in Android Studio) |
| `infra/` | Docker Compose for local infrastructure, load tests |
| `docs/` | Specification, architecture and decision records |

## Requirements

- Java 25
- Docker (Docker Desktop on Windows and macOS)

## Run it

```bash
docker compose -f infra/docker-compose.yml up -d
./gradlew :backend:booking-service:bootRun
```

The booking service listens on http://localhost:8081.

## Reproduce the overselling bug (video 1)

1. Start Postgres and the booking service:

   ```bash
   docker compose -f infra/docker-compose.yml up -d
   ./gradlew :backend:booking-service:bootRun
   ```

2. In a second terminal, send 1,000 buyers at the 100-seat demo event:

   ```bash
   docker compose -f infra/docker-compose.yml --profile loadtest run --rm k6
   ```

3. Look at the result:

   ```bash
   curl http://localhost:8081/events/1/sales
   ```

   `sold` will be far above `capacity`. The exact number changes on every run and every machine.

4. Reset the demo event before the next run:

   ```bash
   docker exec ticketrush-postgres psql -U ticketrush -d booking -f /scripts/reset-demo.sql
   ```

The fix comes in video 2.
