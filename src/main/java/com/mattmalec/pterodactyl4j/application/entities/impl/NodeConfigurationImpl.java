package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.Node;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NodeConfigurationImpl implements Node.Configuration {

    private final JSONObject json;

    public NodeConfigurationImpl(JSONObject json) {
        this.json = json;
    }

    @Override
    public boolean isDebug() {
        return json.getBoolean("debug");
    }

    @Override
    public UUID getUUID() {
        return UUID.fromString(json.getString("uuid"));
    }

    @Override
    public String getTokenId() {
        return json.getString("token_id");
    }

    @Override
    public String getToken() {
        return json.getString("token");
    }

    @Override
    public Node.APIConfiguration getAPI() {
        return new APIConfigurationImpl(json.getJSONObject("api"));
    }

    @Override
    public Node.SystemConfiguration getSystem() {
        return new SystemConfigurationImpl(json.getJSONObject("system"));
    }

    @Override
    public List<String> getAllowedMounts() {
        return Collections.unmodifiableList(
                json.getJSONArray("allowed_mounts").toList()
                        .stream().map(Object::toString).collect(Collectors.toList()));
    }

    @Override
    public String getRemote() {
        return json.getString("remote");
    }

    @Override
    public String toString() {
        return json.toString(4);
    }

    private static class SystemConfigurationImpl implements Node.SystemConfiguration {

        private final JSONObject json;

        protected SystemConfigurationImpl(JSONObject json) {
            this.json = json;
        }

        @Override
        public String getDataPath() {
            return json.getString("data");
        }

        @Override
        public int getSFTPPort() {
            return json.getJSONObject("sftp").getInt("bind_port");
        }
    }

    private static class APIConfigurationImpl implements Node.APIConfiguration {

        private final JSONObject json;

        protected APIConfigurationImpl(JSONObject json) {
            this.json = json;
        }

        @Override
        public String getHost() {
            return json.getString("host");
        }

        @Override
        public int getPort() {
            return json.getInt("port");
        }

        @Override
        public boolean isSSLEnabled() {
            return json.getJSONObject("ssl").getBoolean("enabled");
        }

        @Override
        public String getSSLCertPath() {
            return json.getJSONObject("ssl").optString("cert");
        }

        @Override
        public String getSSLKeyPath() {
            return json.getJSONObject("ssl").optString("key");
        }

        @Override
        public int getUploadLimit() {
            return json.getInt("upload_limit");
        }
    }
}
