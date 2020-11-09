package com.mattmalec.pterodactyl4j;

public enum UtilizationState {

    OFFLINE,
    STARTING,
    RUNNING,
    STOPPING;

    public static UtilizationState of(String s) {
        for(UtilizationState state : values()) {
            if(state.name().equalsIgnoreCase(s)) {
                return state;
            }
        }
        return UtilizationState.OFFLINE;
    }
}
