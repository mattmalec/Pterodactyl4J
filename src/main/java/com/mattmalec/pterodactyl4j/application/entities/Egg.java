package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Optional;

public interface Egg extends ISnowflake {

	Relationed<Nest> getNest();
	Optional<List<EggVariable>> getVariables();
	String getAuthor();
	String getName();
	String getDescription();
	String getDockerImage();
	String getStopCommand();
	String getStartupCommand();
	Script getInstallScript();

	interface EggVariable extends ISnowflake {
		String getName();
		String getDescription();
		String getEnvironmentVariable();
		String getDefaultValue();
		boolean isUserViewable();
		boolean isUserEditable();
		String getRules();
	}

}
