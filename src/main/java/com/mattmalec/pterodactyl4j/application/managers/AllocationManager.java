package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.Node;

public interface AllocationManager {

    AllocationAction createAllocation(Node node);
    PteroAction<Void> deleteAllocation(Node node, Allocation allocation);

}
