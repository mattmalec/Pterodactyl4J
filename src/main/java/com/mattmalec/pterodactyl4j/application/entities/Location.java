package com.mattmalec.pterodactyl4j.application.entities;

import java.util.List;

public interface Location extends ISnowflake {

	String getShortCode();
	String getDescription();
	List<Node> getNodes();

	@Override
	String toString();
}
