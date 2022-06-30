package com.mattmalec.pterodactyl4j;

public enum ClientType {
    NONE(""),
    OWNER("owner"),
    ADMIN_ALL("admin-all"),
    ADMIN("admin");
    
    private final String type;
    ClientType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
