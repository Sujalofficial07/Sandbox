package com.sujal.skyblock.core.api.events;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerStatsUpdateEvent extends SkyBlockEvent {
    private final ServerPlayerEntity player;
    private final SkyBlockStats stats;
    
    public PlayerStatsUpdateEvent(ServerPlayerEntity player, SkyBlockStats stats) {
        this.player = player;
        this.stats = stats;
    }
    
    public ServerPlayerEntity getPlayer() {
        return player;
    }
    
    public SkyBlockStats getStats() {
        return stats;
    }
}
