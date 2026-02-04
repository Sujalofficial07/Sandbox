package com.sujal.skyblock.core.api.events;

import com.sujal.skyblock.core.api.zone.Zone;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class PlayerZoneChangeEvent extends SkyBlockEvent {
    private final ServerPlayerEntity player;
    private final Optional<Zone> fromZone;
    private final Optional<Zone> toZone;
    
    public PlayerZoneChangeEvent(ServerPlayerEntity player, Optional<Zone> fromZone, Optional<Zone> toZone) {
        this.player = player;
        this.fromZone = fromZone;
        this.toZone = toZone;
    }
    
    public ServerPlayerEntity getPlayer() {
        return player;
    }
    
    public Optional<Zone> getFromZone() {
        return fromZone;
    }
    
    public Optional<Zone> getToZone() {
        return toZone;
    }
}
