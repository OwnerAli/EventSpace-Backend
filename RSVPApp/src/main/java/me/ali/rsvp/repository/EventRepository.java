package me.ali.rsvp.repository;

import me.ali.rsvp.event.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, String> {



}
