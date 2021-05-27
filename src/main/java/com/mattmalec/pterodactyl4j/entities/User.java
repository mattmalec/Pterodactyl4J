package com.mattmalec.pterodactyl4j.entities;

/**
 * Represents a Pterodactyl {@link com.mattmalec.pterodactyl4j.entities.User User}.
 * This should contain all information provided from the Pterodactyl instance about a User.
 */
public interface User {

    /**
     * The username of the User
     *
     * @return Never-null String containing the User's username.
     */
    String getUserName();

    /**
     * The email of the User
     *
     * @return Never-null String containing the User's email.
     */
    String getEmail();

}
