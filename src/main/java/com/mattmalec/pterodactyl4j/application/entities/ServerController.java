package com.mattmalec.pterodactyl4j.application.entities;

import com.mattmalec.pterodactyl4j.PteroAction;

public interface ServerController {

	PteroAction<Void> suspend();
	PteroAction<Void> unSuspend();

	PteroAction<Void> reinstall();
	PteroAction<Void> rebuild();

	PteroAction<Void> delete(boolean withForce);



}
