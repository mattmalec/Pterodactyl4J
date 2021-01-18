package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.Optional;

public interface Allocation {

	String getIP();
	String getFullAddress();
	String getAlias();
	String getPort();
	String getNotes();
	@Deprecated
	default long getPortLong() {
		return Long.parseUnsignedLong(getPort());
	}
	default int getPortInt() { return Integer.parseInt(getPort()); }
	boolean isAssigned();
	long getIdLong();
	default String getId() { return Long.toUnsignedString(getIdLong()); }
	Optional<Relationed<ApplicationServer>> getServer();
	Optional<Relationed<Node>> getNode();

}
