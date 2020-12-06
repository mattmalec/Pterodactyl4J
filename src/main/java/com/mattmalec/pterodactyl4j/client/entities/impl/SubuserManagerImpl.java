package com.mattmalec.pterodactyl4j.client.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientSubuser;
import com.mattmalec.pterodactyl4j.client.managers.SubuserAction;
import com.mattmalec.pterodactyl4j.client.managers.SubuserCreationAction;
import com.mattmalec.pterodactyl4j.client.managers.SubuserManager;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;

public class SubuserManagerImpl implements SubuserManager {

    private ClientServer server;
    private PteroClientImpl impl;

    public SubuserManagerImpl(ClientServer server, PteroClientImpl impl) {
        this.server = server;
        this.impl = impl;
    }

    @Override
    public SubuserCreationAction createUser() {
        return new CreateSubuserImpl(server, impl);
    }

    @Override
    public SubuserAction editUser(ClientSubuser subuser) {
        return new EditSubuserImpl(server, subuser, impl);
    }

    @Override
    public PteroAction<Void> deleteUser(ClientSubuser subuser) {
        return PteroActionImpl.onExecute(() -> {
            Route.CompiledRoute route = Route.Subusers.DELETE_SUBUSER.compile(server.getUUID().toString(), subuser.getUUID().toString());
            impl.getRequester().request(route);
            return null;
        });
    }
}
