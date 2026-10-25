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
```
