package com.mattmalec.pterodactyl4j;

public enum ClientType {
    NONE(""),
    OWNER("&type=owner"),
    ADMIN_ALL("&type=admin-all"),
    ADMIN("&type=admin");
    private final String type;
    ClientType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
