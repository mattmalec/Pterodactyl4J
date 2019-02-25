package com.mattmalec.pterodactyl4j.application.entities;


import java.time.OffsetDateTime;
import java.util.List;

public interface Nest extends ISnowflake {

	String getUUID();
	String getAuthor();
	String getName();
	String getDescription();
	List<Egg> getEggs();

}
