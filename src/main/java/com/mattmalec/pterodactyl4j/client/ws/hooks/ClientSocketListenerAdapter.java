package com.mattmalec.pterodactyl4j.client.ws.hooks;

import com.mattmalec.pterodactyl4j.client.ws.events.*;
import com.mattmalec.pterodactyl4j.client.ws.events.connection.*;
import com.mattmalec.pterodactyl4j.client.ws.events.error.DaemonErrorEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.error.JWTErrorEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.output.ConsoleOutputEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.output.InstallOutputEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.output.OutputEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.token.TokenEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.token.TokenExpiredEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.token.TokenExpiringEvent;

public abstract class ClientSocketListenerAdapter implements ClientSocketListener {

    public void onStatusUpdate(StatusUpdateEvent event) {}
    public void onStatsUpdate(StatsUpdateEvent event) {}
    public void onAuthSuccess(AuthSuccessEvent event) {}

    public void onOutput(OutputEvent event) {}
    public void onConsoleOutput(ConsoleOutputEvent event) {}
    public void onInstallOutput(InstallOutputEvent event) {}

    public void onConnectionUpdate(ConnectionEvent event) {}
    public void onConnected(ConnectedEvent event) {}
    public void onDisconnecting(DisconnectingEvent event) {}
    public void onDisconnected(DisconnectedEvent event) {}
    public void onFailure(FailureEvent event) {}


    public void onDaemonError(DaemonErrorEvent event) {}
    public void onJWTError(JWTErrorEvent event) {}

    public void onTokenUpdate(TokenEvent event) {}
    public void onTokenExpiring(TokenExpiringEvent event) {}
    public void onTokenExpired(TokenExpiredEvent event) {}

    public void onGenericEvent(Event event) {}

    @Override
    public final void onEvent(Event event) {
        onGenericEvent(event);
        if (event instanceof StatusUpdateEvent)
            onStatusUpdate((StatusUpdateEvent) event);
        else if (event instanceof StatsUpdateEvent)
            onStatsUpdate((StatsUpdateEvent) event);
        else if (event instanceof AuthSuccessEvent)
            onAuthSuccess((AuthSuccessEvent) event);
        else if (event instanceof ConsoleOutputEvent)
            onConsoleOutput((ConsoleOutputEvent) event);
        else if(event instanceof InstallOutputEvent)
            onInstallOutput((InstallOutputEvent) event);
        else if (event instanceof ConnectedEvent)
            onConnected((ConnectedEvent) event);
        else if (event instanceof DisconnectingEvent)
            onDisconnecting((DisconnectingEvent) event);
        else if (event instanceof DisconnectedEvent)
            onDisconnected((DisconnectedEvent) event);
        else if (event instanceof FailureEvent)
            onFailure((FailureEvent) event);
        else if (event instanceof DaemonErrorEvent)
            onDaemonError((DaemonErrorEvent) event);
        else if (event instanceof JWTErrorEvent)
            onJWTError((JWTErrorEvent) event);
        else if (event instanceof TokenExpiringEvent)
            onTokenExpiring((TokenExpiringEvent) event);
        else if (event instanceof TokenExpiredEvent) {
            onTokenExpired((TokenExpiredEvent) event);
        }

        if(event instanceof OutputEvent)
            onOutput((OutputEvent) event);

        if (event instanceof ConnectionEvent)
            onConnectionUpdate((ConnectionEvent) event);

        if (event instanceof TokenEvent)
            onTokenUpdate((TokenEvent) event);
    }
}
