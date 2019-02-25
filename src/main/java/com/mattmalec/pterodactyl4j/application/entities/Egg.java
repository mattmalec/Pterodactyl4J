package com.mattmalec.pterodactyl4j.application.entities;

import java.time.OffsetDateTime;

public interface Egg extends ISnowflake {

	Nest getNest();
	String getAuthor();
	String getDescription();
	String getDockerImage();
	String getStopCommand();
	String getStartupCommand();
	Script getInstallScript();

}
