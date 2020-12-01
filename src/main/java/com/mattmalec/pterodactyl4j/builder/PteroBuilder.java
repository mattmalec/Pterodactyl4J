package com.mattmalec.pterodactyl4j.builder;

import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

public class PteroBuilder {
    public static PteroApplicationBuilder application(String url, String token) {
        return new PteroApplicationBuilder(url, token);
    }

    public static PteroClientBuilder client(String url, String token) {
        return new PteroClientBuilder(url, token);
    }
}
