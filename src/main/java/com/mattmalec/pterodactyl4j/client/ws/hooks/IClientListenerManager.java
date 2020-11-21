package com.mattmalec.pterodactyl4j.client.ws.hooks;

import com.mattmalec.pterodactyl4j.client.ws.events.Event;

import java.util.List;

public interface IClientListenerManager {

    void register(Object listener);

    void unregister(Object listener);

    List<Object> getRegisteredListeners();

    void handle(Event event);
}
