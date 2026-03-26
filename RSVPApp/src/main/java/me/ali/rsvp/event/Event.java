package me.ali.rsvp.event;

import lombok.Getter;
import lombok.Setter;
import me.ali.rsvp.invites.Invite;
import me.ali.rsvp.people.Attendee;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title, description, address;
    private LocalDateTime startTime, endTime;
    private List<Attendee> attendeeList;
    private List<Invite> inviteList;

    public Event(String title, String description, String address, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendeeList = new ArrayList<>();
        this.inviteList = new ArrayList<>();
        inviteList.addAll(List.of(new Invite(true),
                new Invite(false)));
    }

}
