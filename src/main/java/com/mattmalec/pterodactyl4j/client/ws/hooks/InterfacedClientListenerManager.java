package com.mattmalec.pterodactyl4j.client.ws.hooks;

import com.mattmalec.pterodactyl4j.client.ws.events.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InterfacedClientListenerManager implements IClientListenerManager {

    private static final Logger LISTENER_LOG = LoggerFactory.getLogger(ClientSocketListener.class);

    private final List<ClientSocketListener> listeners;

    public InterfacedClientListenerManager() {
        this.listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void register(Object listener) {
        if (!(listener instanceof ClientSocketListener)) {
            throw new IllegalArgumentException("Listener must implement ClientSocketListener");
        }
        listeners.add((ClientSocketListener) listener);
    }

    @Override
    public void unregister(Object listener) {
        listeners.remove(listener);
    }

    @Override
    public List<Object> getRegisteredListeners() {
        return Collections.unmodifiableList(new LinkedList<>(listeners));
    }

    @Override
    public void handle(Event event) {
        for (ClientSocketListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Throwable throwable) {
                LISTENER_LOG.error("One of the ClientSocketListeners had an uncaught exception", throwable);
            }
        }
    }
}
