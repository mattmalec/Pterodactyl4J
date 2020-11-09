package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.client.entities.SFTP;
import org.json.JSONObject;

public class SFTPImpl implements SFTP {

    private JSONObject json;

    public SFTPImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getIP() {
        return json.getString("ip");
    }

    @Override
    public int getPort() {
        return json.getInt("port");
    }
}
