package com.sujal.skyblock.core.api.events;

import com.sujal.skyblock.core.api.profile.SkyBlockProfile;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerProfileLoadEvent extends SkyBlockEvent {
    private final ServerPlayerEntity player;
    private final SkyBlockProfile profile;
    private final boolean isFirstJoin;
    
    public PlayerProfileLoadEvent(ServerPlayerEntity player, SkyBlockProfile profile, boolean isFirstJoin) {
        this.player = player;
        this.profile = profile;
        this.isFirstJoin = isFirstJoin;
    }
    
    public ServerPlayerEntity getPlayer() {
        return player;
    }
    
    public SkyBlockProfile getProfile() {
        return profile;
    }
    
    public boolean isFirstJoin() {
        return isFirstJoin;
    }
}
