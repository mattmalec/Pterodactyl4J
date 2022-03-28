package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;

/**
 * Represents the status of an {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}
 *
 * @see ApplicationServer#getStatus()
 */
public enum ServerStatus {

    INSTALLING,
    INSTALL_FAILED,
    SUSPENDED,
    RESTORING_BACKUP,
    UNKNOWN

}
