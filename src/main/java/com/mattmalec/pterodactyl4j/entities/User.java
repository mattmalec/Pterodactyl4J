package com.mattmalec.pterodactyl4j.entities;

import java.util.Locale;

public interface User {

    String getUserName();
    String getEmail();
    String getFirstName();
    String getLastName();
    default String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
    String getLanguage();
    default Locale getLocale() {
        return Locale.forLanguageTag(getLanguage());
    }
    boolean isRootAdmin();

}
