package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.application.managers.LocationAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.utils.Relationed;

import java.util.List;

public interface Location extends ISnowflake {

	String getShortCode();
	String getDescription();
	Relationed<List<Node>> getNodes();
	Relationed<List<ApplicationServer>> getServers();
	LocationAction edit();
	PteroAction<Void> delete();

	@Override
	String toString();
}
