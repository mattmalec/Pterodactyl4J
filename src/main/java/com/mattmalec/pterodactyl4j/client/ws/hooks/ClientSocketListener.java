package com.mattmalec.pterodactyl4j.client.ws.hooks;

import com.mattmalec.pterodactyl4j.client.ws.events.Event;

@FunctionalInterface
public interface ClientSocketListener {

    void onEvent(Event event);

}
