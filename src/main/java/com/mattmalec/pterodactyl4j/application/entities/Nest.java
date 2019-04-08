package com.mattmalec.pterodactyl4j.application.entities;


import com.mattmalec.pterodactyl4j.PteroAction;

import java.util.List;

public interface Nest extends ISnowflake {

	String getUUID();
	String getAuthor();
	String getName();
	String getDescription();
	PteroAction<List<Egg>> retrieveEggs();

}
