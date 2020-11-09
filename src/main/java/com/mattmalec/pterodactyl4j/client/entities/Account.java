package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.client.managers.AccountManager;
import com.mattmalec.pterodactyl4j.entities.User;

public interface Account extends User {

    AccountManager getManager();

}
