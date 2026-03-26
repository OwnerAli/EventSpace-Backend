package me.ali.rsvp.registry;

import lombok.Getter;

@Getter
public class RegistryManager {

    public static final RegistryManager registryManager;

    static {
        registryManager = new RegistryManager();
    }

    private final EventRegistry eventRegistry = new EventRegistry();

}
