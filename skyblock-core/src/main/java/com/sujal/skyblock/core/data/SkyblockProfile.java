package com.sujal.skyblock.core.data;

import com.sujal.skyblock.core.api.StatType;
import net.minecraft.nbt.NbtCompound;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkyblockProfile {
    private final UUID playerUUID;
    
    // Currency
    private double coins;
    private double bankBalance;
    private long lastInterestTime;
    
    // Gameplay
    private double currentMana;
    
    // Data Storage
    private final Map<StatType, Double> statMultipliers;
    private final Map<String, Double> skillXp;

    public SkyblockProfile(UUID uuid) {
        this.playerUUID = uuid;
        this.coins = 0.0;
        this.bankBalance = 0.0;
        this.lastInterestTime = System.currentTimeMillis();
        this.currentMana = 100.0; // Default Mana
        
        this.statMultipliers = new EnumMap<>(StatType.class);
        this.skillXp = new HashMap<>();

        // Initialize default stat bonuses (0.0)
        for (StatType stat : StatType.values()) {
            statMultipliers.put(stat, 0.0);
        }
    }

    // --- GETTERS ---
    public UUID getUuid() { return playerUUID; }
    public double getCoins() { return coins; }
    public double getBankBalance() { return bankBalance; }
    public double getCurrentMana() { return currentMana; }
    public long getLastInterestTime() { return lastInterestTime; }

    // --- CURRENCY LOGIC ---
    public void addCoins(double amount) {
        this.coins += amount;
    }
    
    public void removeCoins(double amount) {
        this.coins = Math.max(0, this.coins - amount);
    }

    public void addToBank(double amount) {
        this.bankBalance += amount;
    }

    public void updateInterestTime() {
        this.lastInterestTime = System.currentTimeMillis();
    }

    // --- MANA LOGIC ---
    public boolean consumeMana(double amount) {
        if (this.currentMana >= amount) {
            this.currentMana -= amount;
            return true;
        }
        return false;
    }

    public void regenMana(double maxMana) {
        // Regen 2% of Max Mana per second (called every tick usually, so divide appropriately logic handles elsewhere)
        // Here we just accept the new value logic or increment.
        // Simple Logic: Add 2% of Max Mana divided by 20 (per tick)
        double regenPerTick = (maxMana * 0.02) / 20.0;
        this.currentMana = Math.min(maxMana, this.currentMana + regenPerTick);
    }

    // --- STATS & SKILLS ---
    public double getStatBonus(StatType type) {
        return statMultipliers.getOrDefault(type, 0.0);
    }

    public void setStatBonus(StatType type, double value) {
        statMultipliers.put(type, value);
    }

    public void addSkillXp(String skillName, double xp) {
        this.skillXp.put(skillName, this.skillXp.getOrDefault(skillName, 0.0) + xp);
    }
    
    public double getSkillXp(String skillName) {
        return this.skillXp.getOrDefault(skillName, 0.0);
    }

    // --- NBT SERIALIZATION (SAVE/LOAD) ---
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putUuid("OwnerUUID", playerUUID);
        tag.putDouble("Coins", coins);
        tag.putDouble("BankBalance", bankBalance);
        tag.putLong("LastInterest", lastInterestTime);
        tag.putDouble("CurrentMana", currentMana);

        NbtCompound statsTag = new NbtCompound();
        statMultipliers.forEach((stat, val) -> statsTag.putDouble(stat.name(), val));
        tag.put("Stats", statsTag);

        NbtCompound skillsTag = new NbtCompound();
        skillXp.forEach(skillsTag::putDouble);
        tag.put("Skills", skillsTag);

        return tag;
    }

    public static SkyblockProfile fromNbt(NbtCompound tag) {
        UUID uuid;
        if (tag.contains("OwnerUUID")) {
            uuid = tag.getUuid("OwnerUUID");
        } else {
            // Fallback for safety
            uuid = UUID.randomUUID(); 
        }
        
        SkyblockProfile profile = new SkyblockProfile(uuid);
        
        if (tag.contains("Coins")) profile.coins = tag.getDouble("Coins");
        if (tag.contains("BankBalance")) profile.bankBalance = tag.getDouble("BankBalance");
        if (tag.contains("LastInterest")) profile.lastInterestTime = tag.getLong("LastInterest");
        if (tag.contains("CurrentMana")) profile.currentMana = tag.getDouble("CurrentMana");

        if (tag.contains("Stats")) {
            NbtCompound statsTag = tag.getCompound("Stats");
            for (String key : statsTag.getKeys()) {
                try {
                    StatType type = StatType.valueOf(key);
                    profile.statMultipliers.put(type, statsTag.getDouble(key));
                } catch (IllegalArgumentException ignored) {}
            }
        }

        if (tag.contains("Skills")) {
            NbtCompound skillsTag = tag.getCompound("Skills");
            for (String key : skillsTag.getKeys()) {
                profile.skillXp.put(key, skillsTag.getDouble(key));
            }
        }

        return profile;
    }
}
