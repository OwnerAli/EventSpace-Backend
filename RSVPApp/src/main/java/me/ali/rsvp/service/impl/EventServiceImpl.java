package me.ali.rsvp.service.impl;

import me.ali.rsvp.event.Event;
import me.ali.rsvp.people.Attendee;
import me.ali.rsvp.repository.EventRepository;
import me.ali.rsvp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> fetchEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event getEventById(String id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public void addEventAttendee(String eventId, Attendee attendee) {
        Event eventById = getEventById(eventId);
        eventById.getAttendeeList()
                .add(attendee);
        eventRepository.save(eventById);
    }

    @Override
    public void deleteEventById(String id) {
        eventRepository.deleteById(id);
    }

}
