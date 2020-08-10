package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.entities.Server;

public interface ClientServer extends Server {

	boolean isServerOwner();

}
