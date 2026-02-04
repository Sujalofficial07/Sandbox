package com.sujal.skyblock.core.api.profile;

import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public interface SkyBlockProfile {
    UUID getUUID();
    
    String getProfileName();
    
    void setProfileName(String name);
    
    long getCoins();
    
    void setCoins(long coins);
    
    void addCoins(long amount);
    
    boolean removeCoins(long amount);
    
    long getBankCoins();
    
    void setBankCoins(long coins);
    
    void depositBank(long amount);
    
    boolean withdrawBank(long amount);
    
    SkyBlockStats getStats();
    
    int getSkyBlockLevel();
    
    void setSkyBlockLevel(int level);
    
    long getSkyBlockXP();
    
    void addSkyBlockXP(long xp);
    
    ProfileData getData();
    
    void save();
    
    void load();
    
    ServerPlayerEntity getPlayer();
    
    long getFirstJoinTime();
    
    long getLastSeenTime();
    
    void updateLastSeen();
    
    boolean isFirstJoin();
    
    void markNotFirstJoin();
}
