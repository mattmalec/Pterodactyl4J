package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;

public interface AllocationManager {

    AllocationAction createAllocation();
    PteroAction<Void> deleteAllocation(Allocation allocation);

}
