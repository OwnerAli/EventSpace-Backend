package me.ali.rsvp.invites;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Invite {

    private String id;
    private boolean plusOne;

    public Invite(boolean plusOne) {
        this.id = UUID.randomUUID().toString();
        this.plusOne = plusOne;
    }

}
