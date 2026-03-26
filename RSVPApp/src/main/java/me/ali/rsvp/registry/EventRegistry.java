package me.ali.rsvp.registry;

import lombok.Getter;
import me.ali.rsvp.event.Event;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventRegistry {

    public final List<Event> eventsList = new ArrayList<>();

}
