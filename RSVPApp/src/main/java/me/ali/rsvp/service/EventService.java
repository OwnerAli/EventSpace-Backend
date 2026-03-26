package me.ali.rsvp.service;

import me.ali.rsvp.event.Event;
import me.ali.rsvp.people.Attendee;

import java.util.List;
import java.util.UUID;

public interface EventService {

    Event create(Event event);

    List<Event> fetchEvents();

    Event getEventById(String id);

    void addEventAttendee(String eventId, Attendee attendee);

    void deleteEventById(String id);

}
