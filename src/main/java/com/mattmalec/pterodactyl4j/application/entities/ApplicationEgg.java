package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.entities.Egg;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplicationEgg extends Egg, ISnowflake {

	Relationed<Nest> getNest();
	Optional<List<EggVariable>> getVariables();
	Optional<Map<String, EnvironmentValue<?>>> getDefaultVariableMap();
	String getAuthor();
	String getDescription();
	String getDockerImage();
	String getStopCommand();
	String getStartupCommand();
	Script getInstallScript();

	interface EggVariable extends Egg.EggVariable, ISnowflake {
		boolean isUserViewable();
		boolean isUserEditable();
	}

}
