package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface NodeManager {

    NodeAction createNode();
    NodeAction editNode(Node node);
    PteroAction<Void> deleteNode(Node node);



}
