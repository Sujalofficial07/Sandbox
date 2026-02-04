package com.sujal.skyblock.core.internal.zone;

import com.sujal.skyblock.core.api.zone.Zone;
import net.minecraft.server.network.ServerPlayerEntity;

public class ZoneDetector {
    private final ZoneManager zoneManager;
    
    public ZoneDetector(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }
    
    public boolean canPlayerEnter(ServerPlayerEntity player, Zone zone) {
        return true;
    }
}
