package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;

public interface Egg extends ISnowflake {

	PteroAction<Nest> retrieveNest();
	String getAuthor();
	String getDescription();
	String getDockerImage();
	String getStopCommand();
	String getStartupCommand();
	Script getInstallScript();

}
