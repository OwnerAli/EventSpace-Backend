package me.ali.rsvp.controller;

import me.ali.rsvp.event.Event;
import me.ali.rsvp.invites.Invite;
import me.ali.rsvp.people.Attendee;
import me.ali.rsvp.registry.RegistryManager;
import me.ali.rsvp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    private final List<Event> eventList = RegistryManager.registryManager.getEventRegistry().getEventsList();

    @GetMapping("/get/{id}")
    public List<Event> getEventsList(@PathVariable String id) {
        return eventList.stream()
                .filter(event -> event.getId().equals(id))
                .toList();
    }

    @GetMapping("/get/1")
    @ResponseStatus(HttpStatus.OK)
    public Event createEvent() {
        Event event = new Event("Ali's Wedding", "Test Event", "Test",
                LocalDateTime.now(), LocalDateTime.now());
//        event.setId("1");
        event.getAttendeeList().add(new Attendee("Ali", "111", "test", "true", true));
        eventList.add(event);
        return event;
    }

    @GetMapping("/attendees/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Attendee> getAttendees(@PathVariable String eventId) {
        return eventService.getEventById(eventId).getAttendeeList();
    }

    @GetMapping("/invites/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invite> getInvites(@PathVariable String eventId) {
        if (eventService.getEventById(eventId) == null) {
            return new ArrayList<>();
        }
        return eventService.getEventById(eventId).getInviteList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Event createEvent(@RequestBody Event event) {
        return eventService.create(event);
    }

    @PostMapping("/invite/accept/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Attendee> addEventAttendee(@PathVariable String eventId, @RequestBody Attendee attendee) {
        eventService.addEventAttendee(eventId, attendee);

        return eventService.getEventById(eventId).getAttendeeList();
    }

}
