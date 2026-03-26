package me.ali.rsvp.controller;

import me.ali.rsvp.event.Event;
import me.ali.rsvp.invites.Invite;
import me.ali.rsvp.service.EventService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/invite")
@CrossOrigin
public class InviteController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{inviteId}")
    public ResponseEntity<Event> getEventByInviteId(@PathVariable String inviteId) {
        Optional<Event> eventOptional = eventService.fetchEvents().stream()
                .filter(event -> event.getInviteList()
                        .stream()
                        .anyMatch(invite -> invite.getId().equals(inviteId)))
                .findAny();

        return eventOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/plusone/{inviteId}")
    public ResponseEntity<Boolean> getPlusOneStatusFromInvite(@PathVariable String inviteId) {
        Optional<Boolean> plusOneOptional = eventService.fetchEvents().stream()
                .filter(event -> event.getInviteList()
                        .stream()
                        .anyMatch(invite -> invite.getId().equals(inviteId)))
                .findAny()
                .map(event -> event.getInviteList()
                        .stream()
                        .filter(invite -> invite.getId().equals(inviteId))
                        .findAny()
                        .map(Invite::isPlusOne)
                        .orElse(false));

        return plusOneOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}