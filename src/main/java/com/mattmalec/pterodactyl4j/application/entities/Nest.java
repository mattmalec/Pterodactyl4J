package com.mattmalec.pterodactyl4j.application.entities;


import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Optional;

public interface Nest extends ISnowflake {

	String getUUID();
	String getAuthor();
	String getName();
	String getDescription();
	Relationed<List<Egg>> getEggs();
	Optional<List<ApplicationServer>> getServers();

}
