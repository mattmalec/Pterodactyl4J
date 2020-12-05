package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.managers.abstracts.AbstractNodeAction;
import com.mattmalec.pterodactyl4j.entities.PteroAction;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import static java.lang.Long.parseLong;

public class EditNodeImpl extends AbstractNodeAction {

    private final Node node;

    EditNodeImpl(PteroApplicationImpl impl, Node node) {
        super(impl);
        this.node = node;
    }

    @Override
    public PteroAction<Node> build() {
        return PteroActionImpl.onExecute(() -> {
            JSONObject json = new JSONObject();

            String nameToPut = (name == null) ? node.getName() : name;
            String isPublic = boolBit((this.isPublic == null) ? node.isPublic() : this.isPublic);
            long memoryToPut = (memory == null) ? node.getMemoryLong() : parseLong(memory);
            String fqdnToPut = (fqdn == null) ? node.getFQDN() : fqdn;
            String schemeToPut = (secure == null) ? node.getScheme() : (secure ? "https" : "http");
            long diskSpaceToPut = (diskSpace == null) ? node.getIdLong() : parseLong(diskSpace);
            String locationToPut = (location == null) ? node.getLocation().retrieve().execute().getId() : location.getId();
            String isBehindProxy = boolBit((this.isBehindProxy == null) ? node.isBehindProxy() : this.isBehindProxy);
            boolean throttleToPut = (throttle != null) && throttle;
            String daemonBaseToPut = (daemonBase == null) ? node.getDaemonBase() : daemonBase;
            long memoryOverallocate = (this.memoryOverallocate == null) ? node.getMemoryOverallocateLong() : parseLong(this.memoryOverallocate);
            long diskSpaceOverallocate = (this.diskSpaceOverallocate == null) ? node.getDiskOverallocateLong() : parseLong(this.diskSpaceOverallocate);
            String daemonSFTPPortToPut = (daemonSFTPPort == null) ? node.getDaemonSFTPPort() : daemonSFTPPort;
            String deamonListenPortToPut = (daemonListenPort == null) ? node.getDaemonListenPort() : daemonListenPort;

            json.put("name", nameToPut);
            json.put("fqdn", fqdnToPut);
            json.put("public", isPublic);
            json.put("scheme", schemeToPut);
            json.put("memory", memoryToPut);
            json.put("disk", diskSpaceToPut);
            json.put("location_id", locationToPut);
            json.put("behind_proxy", isBehindProxy);
            json.put("daemon_base", daemonBaseToPut);
            json.put("daemon_sftp", daemonSFTPPortToPut);
            json.put("daemon_listen", deamonListenPortToPut);
            json.put("memory_overallocate", memoryOverallocate);
            json.put("disk_overallocate", diskSpaceOverallocate);
            json.put("throttle", new JSONObject().put("enabled", throttleToPut));


            Route.CompiledRoute route = Route.Nodes.EDIT_NODE.compile(node.getId()).withJSONdata(json);
            JSONObject jsonObject = requester.request(route).toJSONObject();
            return new NodeImpl(jsonObject, impl);
        });
    }

    private static String boolBit(boolean in) {
        return in ? "1" : "0";
    }
}
