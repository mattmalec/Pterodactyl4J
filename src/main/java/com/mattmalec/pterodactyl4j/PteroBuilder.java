package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.entities.PteroAPI;
import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;

public class PteroBuilder {

    private String token;
    private String applicationUrl;
    private AccountType accountType;

    public PteroBuilder(String applicationUrl, String token) {
        this.token = token;
        this.applicationUrl = applicationUrl;
    }
    public PteroBuilder(AccountType accountType) {
        this.accountType = accountType;
    }

    public PteroBuilder setToken(String token) {
        this.token = token;
        return this;
    }


    public PteroBuilder setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
        return this;
    }
    public PteroBuilder setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public String getToken() {
        return this.token;
    }


    public String getApplicationUrl() {
        return this.applicationUrl;
    }
    public PteroAPI build() {
        if(this.applicationUrl == null || this.applicationUrl.isEmpty())
            throw new IllegalArgumentException("Application URL cannot be blank or null");
        if(this.token == null || this.token.isEmpty())
            throw new IllegalArgumentException("Authorization token cannot be blank or null");
        return new PteroAPIImpl(this.applicationUrl, this.token, accountType);
    }
}
