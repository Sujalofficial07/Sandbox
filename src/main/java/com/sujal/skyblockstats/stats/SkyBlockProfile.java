package com.sujal.skyblockstats.stats;

import net.minecraft.nbt.NbtCompound;
import java.util.UUID;

public class SkyBlockProfile {
    private final UUID profileId;
    private final StatContainer statContainer;
    
    // Economy
    private double coins;
    private double bits;

    // Dynamic State (Current values, not max)
    private double currentMana;

    public SkyBlockProfile() {
        this.profileId = UUID.randomUUID();
        this.statContainer = new StatContainer();
        this.coins = 0.0;
        this.bits = 0.0;
        this.currentMana = 100.0; // Starting Mana
    }

    public StatContainer getStatContainer() {
        return statContainer;
    }

    // --- Economy Methods ---
    public double getCoins() { return coins; }
    public void addCoins(double amount) { this.coins += amount; }
    public boolean removeCoins(double amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        }
        return false;
    }

    // --- Mana Methods ---
    public double getCurrentMana() { return currentMana; }
    public void setCurrentMana(double mana) { this.currentMana = mana; }
    public void consumeMana(double amount) { this.currentMana = Math.max(0, this.currentMana - amount); }

    public void writeToNbt(NbtCompound tag) {
        tag.putUuid("ProfileId", profileId);
        tag.putDouble("Coins", coins);
        tag.putDouble("Bits", bits);
        tag.putDouble("CurrentMana", currentMana);
        
        NbtCompound statsTag = new NbtCompound();
        statContainer.writeToNbt(statsTag);
        tag.put("Stats", statsTag);
    }

    public void readFromNbt(NbtCompound tag) {
        this.coins = tag.getDouble("Coins");
        this.bits = tag.getDouble("Bits");
        if (tag.contains("CurrentMana")) {
            this.currentMana = tag.getDouble("CurrentMana");
        }
        if (tag.contains("Stats")) {
            statContainer.readFromNbt(tag.getCompound("Stats"));
        }
    }
}
