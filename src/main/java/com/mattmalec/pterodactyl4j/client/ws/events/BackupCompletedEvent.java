package com.mattmalec.pterodactyl4j.client.ws.events;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import org.json.JSONObject;

public class BackupCompletedEvent extends Event {

	private JSONObject json;

	public BackupCompletedEvent(PteroClientImpl api, ClientServer server, WebSocketManager manager, JSONObject json) {
		super(api, server, manager);
		this.json = json;
	}

	public boolean isSuccessful() {
		return json.getBoolean("is_successful");
	}

	public String getChecksum() {
		return json.getString("checksum");
	}

	public String getChecksumType() {
		return json.getString("checksum_type");
	}

	public long getSize() {
		return json.getLong("file_size");
	}

	public String getSizeFormatted(DataType dataType) {
		return String.format("%.2f %s", getSize() / (dataType.getMbValue() * Math.pow(2, 20)), dataType.name());
	}
}
