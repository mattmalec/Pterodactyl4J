package com.mattmalec.pterodactyl4j.client.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.impl.PteroClientImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountManager {

    private PteroClientImpl impl;

    public AccountManager(PteroClientImpl impl) {
        this.impl = impl;
    }

    public PteroAction<String> get2FAImage() {
        return new PteroActionImpl<>(impl.getPteroApi(), Route.Accounts.GET_2FA_CODE.compile(),
                (response, request) -> response.getObject().getJSONObject("data").getString("image_url_data"));
    }

    public PteroAction<Set<String>> enable2FA(int code) {
        JSONObject obj = new JSONObject().put("code", code);
        return new PteroActionImpl<>(impl.getPteroApi(),
                Route.Accounts.ENABLE_2FA.compile(), PteroActionImpl.getRequestBody(obj),
                (response, request) -> Collections.unmodifiableSet(response.getObject().getJSONObject("attributes")
                        .getJSONArray("tokens").toList().stream().map(Object::toString).collect(Collectors.toSet())));
    }

    public PteroAction<Void> disable2FA(String password) {
        JSONObject obj = new JSONObject().put("password", password);
        return PteroActionImpl.onRequestExecute(impl.getPteroApi(),  Route.Accounts.DISABLE_2FA.compile(),
                PteroActionImpl.getRequestBody(obj));
    }

    public PteroAction<Void> updateEmail(String newEmail, String password) {
        JSONObject obj = new JSONObject()
                .put("email", newEmail)
                .put("password", password);
        return PteroActionImpl.onRequestExecute(impl.getPteroApi(),  Route.Accounts.UPDATE_EMAIL.compile(),
                PteroActionImpl.getRequestBody(obj));
    }

    public PteroAction<Void> updatePassword(String currentPassword, String newPassword) {
        JSONObject obj = new JSONObject()
                .put("current_password", currentPassword)
                .put("password", newPassword)
                .put("password_confirmation", newPassword);
        return PteroActionImpl.onRequestExecute(impl.getPteroApi(),  Route.Accounts.UPDATE_PASSWORD.compile(),
                PteroActionImpl.getRequestBody(obj));
    }
}
