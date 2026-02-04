package com.sujal.skyblock.core.internal.profile;

import com.sujal.skyblock.core.api.profile.ProfileData;
import com.sujal.skyblock.core.api.profile.SkyBlockProfile;
import com.sujal.skyblock.core.api.stats.SkyBlockStats;
import com.sujal.skyblock.core.api.stats.SkyBlockStatsAPI;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class ProfileImpl implements SkyBlockProfile {
    private final UUID uuid;
    private final ServerPlayerEntity player;
    private final ProfileData data;
    
    private String profileName;
    private long coins;
    private long bankCoins;
    private int skyBlockLevel;
    private long skyBlockXP;
    private long firstJoinTime;
    private long lastSeenTime;
    private boolean firstJoin;
    
    public ProfileImpl(ServerPlayerEntity player) {
        this.uuid = player.getUuid();
        this.player = player;
        this.data = new ProfileData();
        this.profileName = player.getName().getString();
        this.coins = 0;
        this.bankCoins = 0;
        this.skyBlockLevel = 1;
        this.skyBlockXP = 0;
        this.firstJoinTime = System.currentTimeMillis();
        this.lastSeenTime = System.currentTimeMillis();
        this.firstJoin = true;
    }
    
    @Override
    public UUID getUUID() {
        return uuid;
    }
    
    @Override
    public String getProfileName() {
        return profileName;
    }
    
    @Override
    public void setProfileName(String name) {
        this.profileName = name;
    }
    
    @Override
    public long getCoins() {
        return coins;
    }
    
    @Override
    public void setCoins(long coins) {
        this.coins = Math.max(0, coins);
    }
    
    @Override
    public void addCoins(long amount) {
        this.coins += amount;
        if (this.coins < 0) this.coins = 0;
    }
    
    @Override
    public boolean removeCoins(long amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        }
        return false;
    }
    
    @Override
    public long getBankCoins() {
        return bankCoins;
    }
    
    @Override
    public void setBankCoins(long coins) {
        this.bankCoins = Math.max(0, coins);
    }
    
    @Override
    public void depositBank(long amount) {
        if (removeCoins(amount)) {
            this.bankCoins += amount;
        }
    }
    
    @Override
    public boolean withdrawBank(long amount) {
        if (this.bankCoins >= amount) {
            this.bankCoins -= amount;
            addCoins(amount);
            return true;
        }
        return false;
    }
    
    @Override
    public SkyBlockStats getStats() {
        return SkyBlockStatsAPI.getStats(player).orElse(null);
    }
    
    @Override
    public int getSkyBlockLevel() {
        return skyBlockLevel;
    }
    
    @Override
    public void setSkyBlockLevel(int level) {
        this.skyBlockLevel = Math.max(1, level);
    }
    
    @Override
    public long getSkyBlockXP() {
        return skyBlockXP;
    }
    
    @Override
    public void addSkyBlockXP(long xp) {
        this.skyBlockXP += xp;
    }
    
    @Override
    public ProfileData getData() {
        return data;
    }
    
    @Override
    public void save() {
    }
    
    @Override
    public void load() {
    }
    
    @Override
    public ServerPlayerEntity getPlayer() {
        return player;
    }
    
    @Override
    public long getFirstJoinTime() {
        return firstJoinTime;
    }
    
    @Override
    public long getLastSeenTime() {
        return lastSeenTime;
    }
    
    @Override
    public void updateLastSeen() {
        this.lastSeenTime = System.currentTimeMillis();
    }
    
    @Override
    public boolean isFirstJoin() {
        return firstJoin;
    }
    
    @Override
    public void markNotFirstJoin() {
        this.firstJoin = false;
    }
    
    void setFirstJoinTime(long time) {
        this.firstJoinTime = time;
    }
    
    void setLastSeenTime(long time) {
        this.lastSeenTime = time;
    }
    
    void setFirstJoin(boolean firstJoin) {
        this.firstJoin = firstJoin;
    }
    
    void setSkyBlockXP(long xp) {
        this.skyBlockXP = xp;
    }
}
