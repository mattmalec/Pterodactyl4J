package com.mattmalec.pterodactyl4j.utils;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private final String name;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, String.format("P4J-%s-Worker", name));
        thread.setDaemon(true);
        return thread;
    }
}
