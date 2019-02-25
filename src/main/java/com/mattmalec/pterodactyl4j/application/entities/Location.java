package com.mattmalec.pterodactyl4j.application.entities;

import java.time.OffsetDateTime;
import java.util.List;

public interface Location {

	String getId();
	default long getIdLong() { return Long.parseLong(getId()); }
	String getName();
	String getDescription();
	OffsetDateTime getCreationDate();
	OffsetDateTime getUpdatedDate();
	List<Node> getNodes();
}
