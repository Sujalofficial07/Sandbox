package com.sujal.skyblock.core.api.events;

import net.minecraft.server.MinecraftServer;

public class SkyBlockTickEvent extends SkyBlockEvent {
    private final MinecraftServer server;
    private final long tickCount;
    
    public SkyBlockTickEvent(MinecraftServer server, long tickCount) {
        this.server = server;
        this.tickCount = tickCount;
    }
    
    public MinecraftServer getServer() {
        return server;
    }
    
    public long getTickCount() {
        return tickCount;
    }
}
