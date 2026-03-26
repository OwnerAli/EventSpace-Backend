# RSVP Backend — Spring Boot

A REST backend service for an event management and RSVP application. Built with **Spring Boot**, it handles event creation, invite management, and attendee tracking.

---

## Tech Stack

- **Java** + **Spring Boot**
- **Spring Web** (REST controllers)
- **Docker** (containerization)

---

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker (optional)

### Run Locally

```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

### Run with Docker

```bash
docker build -t rsvp-backend .
docker run -p 8080:8080 rsvp-backend
```

---

## API Reference

### Events — `/events`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/events/get/{id}` | Get event(s) by event ID |
| `GET` | `/events/get/1` | Seed a test event (dev utility) |
| `GET` | `/events/attendees/{eventId}` | Get all attendees for an event |
| `GET` | `/events/invites/{eventId}` | Get all invites for an event |
| `POST` | `/events/create` | Create a new event |
| `POST` | `/events/invite/accept/{eventId}` | Accept an invite and add attendee |

---

#### `GET /events/get/{id}`

Returns a list of events matching the given ID.

**Path Params:** `id` — event ID (UUID string)

**Response:** `200 OK`
```json
[
  {
    "id": "abc-123",
    "name": "Ali's Wedding",
    "description": "Test Event",
    "location": "Test",
    "startTime": "2025-06-01T14:00:00",
    "endTime": "2025-06-01T20:00:00",
    "attendeeList": [],
    "inviteList": []
  }
]
```

---

#### `GET /events/get/1` *(Dev Only)*

Seeds and returns a hardcoded test event with one attendee. Intended for development/testing purposes only.

**Response:** `200 OK` — returns the created `Event` object.

---

#### `GET /events/attendees/{eventId}`

Returns the list of attendees for a given event.

**Path Params:** `eventId` — event ID

**Response:** `200 OK`
```json
[
  {
    "name": "Ali",
    "phone": "111",
    "email": "test",
    "rsvpStatus": "true",
    "plusOne": true
  }
]
```

---

#### `GET /events/invites/{eventId}`

Returns the invite list for a given event. Returns an empty array if the event is not found.

**Path Params:** `eventId` — event ID

**Response:** `200 OK`
```json
[
  {
    "id": "invite-uuid",
    "plusOne": true
  }
]
```

---

#### `POST /events/create`

Creates a new event.

**Request Body:**
```json
{
  "name": "Birthday Party",
  "description": "Turning 30!",
  "location": "New York",
  "startTime": "2025-07-10T18:00:00",
  "endTime": "2025-07-10T23:00:00"
}
```

**Response:** `200 OK` — returns the created `Event` object with a generated ID.

---

#### `POST /events/invite/accept/{eventId}`

Accepts an invite for an event and adds the person as an attendee.

**Path Params:** `eventId` — event ID

**Request Body:**
```json
{
  "name": "Jane Doe",
  "phone": "555-1234",
  "email": "jane@example.com",
  "rsvpStatus": "true",
  "plusOne": false
}
```

**Response:** `200 OK` — returns the updated attendee list.

---

### Invites — `/invite`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/invite/{inviteId}` | Get the event associated with an invite |
| `GET` | `/invite/plusone/{inviteId}` | Check if an invite allows a plus-one |

---

#### `GET /invite/{inviteId}`

Looks up the event that contains the given invite ID.

**Path Params:** `inviteId` — invite UUID

**Response:**
- `200 OK` — returns the matching `Event` object
- `404 Not Found` — if no matching invite exists

---

#### `GET /invite/plusone/{inviteId}`

Returns whether the invite permits a plus-one guest.

**Path Params:** `inviteId` — invite UUID

**Response:**
- `200 OK` — `true` or `false`
- `404 Not Found` — if invite does not exist

---

## Project Structure

```
src/main/java/me/ali/rsvp/
├── controller/
│   ├── EventController.java     # Event CRUD + attendee management
│   └── InviteController.java    # Invite lookup endpoints
├── event/
│   └── Event.java               # Event model
├── invites/
│   └── Invite.java              # Invite model
├── people/
│   └── Attendee.java            # Attendee model
├── registry/
│   └── RegistryManager.java     # In-memory event store
└── service/
    └── EventService.java        # Business logic layer
```

---

## Notes

- Data is stored **in-memory** via `RegistryManager`. There is no database persistence — all data resets on restart.
- CORS is enabled globally via `@CrossOrigin` on all controllers.
- `GET /events/get/1` is a dev-only seed endpoint and should be removed or gated before production deployment.
