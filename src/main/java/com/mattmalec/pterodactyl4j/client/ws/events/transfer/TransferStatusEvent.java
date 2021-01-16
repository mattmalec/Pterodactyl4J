package com.mattmalec.pterodactyl4j.client.ws.events.transfer;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.Event;

public class TransferStatusEvent extends Event {

	private String status;

	public TransferStatusEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, String status) {
		super(api, server, manager);
		this.status = status;
	}

	public TransferStatus getStatus() {
		return TransferStatus.valueOf(status.toUpperCase());
	}

	enum TransferStatus {
		STARTING,
		ARCHIVED,
		SUCCESS,
		FAILURE
	}

}
