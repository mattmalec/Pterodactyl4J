package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.client.managers.AccountManager;
import com.mattmalec.pterodactyl4j.entities.User;

import java.util.Locale;

public interface Account extends User {

    String getFirstName();
    String getLastName();
    default String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
    long getId();
    boolean isRootAdmin();
    String getLanguage();
    default Locale getLocale() {
        return Locale.forLanguageTag(getLanguage());
    }

    AccountManager getManager();

}
