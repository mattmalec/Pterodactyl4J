package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.entities.PteroAction;

public interface AllocationManager {

    AllocationAction createAllocation();
    PteroAction<Void> deleteAllocation(Allocation allocation);

}
