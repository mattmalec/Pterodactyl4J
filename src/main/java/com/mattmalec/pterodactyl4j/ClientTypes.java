package com.mattmalec.pterodactyl4j;

public enum ClientTypes {
    NONE(""),
    OWNER("&type=owner"),
    ADMIN_ALL("&type=admin-all"),
    ADMIN("&type=admin");
    private final String type;
    ClientTypes(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
