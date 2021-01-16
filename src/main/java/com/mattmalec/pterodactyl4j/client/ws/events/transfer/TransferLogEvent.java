package com.mattmalec.pterodactyl4j.client.ws.events.transfer;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public class TransferLogEvent extends Event {

	private String line;

	public TransferLogEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, String line) {
		super(api, server, manager);
		this.line = line;
	}

	public String getLine() {
		return line;
	}

}
