package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Node;

public interface NodeManager {

    NodeAction createNode();
    NodeAction editNode(Node node);
    PteroAction<Void> deleteNode(Node node);



}
