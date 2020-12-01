package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AccountManager {

    private final PteroClientImpl impl;

    public AccountManager(PteroClientImpl impl) {
        this.impl = impl;
    }

    public PteroAction<String> get2FAImage() {
        return PteroActionImpl.onExecute(() ->
        {
            Route.CompiledRoute route = Route.Accounts.GET_2FA_CODE.compile();
            JSONObject json = impl.getRequester().request(route).toJSONObject();
            return json.getJSONObject("data").getString("image_url_data");
        });
    }

    public PteroAction<Set<String>> enable2FA(int code) {
        return PteroActionImpl.onExecute(() ->
        {
            JSONObject obj = new JSONObject().put("code", code);
            Route.CompiledRoute route = Route.Accounts.ENABLE_2FA.compile().withJSONdata(obj);
            JSONObject json = impl.getRequester().request(route).toJSONObject();
            JSONArray array = json.getJSONObject("attributes").getJSONArray("tokens");
            Set<String> codes = new HashSet<>();
            array.forEach(o -> codes.add(o.toString()));
            return Collections.unmodifiableSet(codes);
        });
    }

    public PteroAction<Void> disable2FA(String password) {
        return PteroActionImpl.onExecute(() ->
        {
            JSONObject obj = new JSONObject().put("password", password);
            Route.CompiledRoute route = Route.Accounts.DISABLE_2FA.compile().withJSONdata(obj);
            impl.getRequester().request(route);
            return null;
        });
    }

    public PteroAction<Void> updateEmail(String newEmail, String password) {
        return PteroActionImpl.onExecute(() ->
        {
            JSONObject obj = new JSONObject()
                    .put("email", newEmail)
                    .put("password", password);
            Route.CompiledRoute route = Route.Accounts.UPDATE_EMAIL.compile().withJSONdata(obj);
            impl.getRequester().request(route);
            return null;
        });
    }

    public PteroAction<Void> updatePassword(String currentPassword, String newPassword) {
        return PteroActionImpl.onExecute(() ->
        {
            JSONObject obj = new JSONObject()
                    .put("current_password", currentPassword)
                    .put("password", newPassword)
                    .put("password_confirmation", newPassword);
            Route.CompiledRoute route = Route.Accounts.UPDATE_PASSWORD.compile().withJSONdata(obj);
            impl.getRequester().request(route);
            return null;
        });
    }
}
