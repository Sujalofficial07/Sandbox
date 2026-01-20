package com.sujal.skyblock.core.data;

import com.sujal.skyblock.core.api.StatType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkyblockProfile {
    private final UUID playerUUID;
    private double coins;
    private double bankBalance;
    private final Map<StatType, Double> statMultipliers;
    private final Map<String, Integer> skillLevels;
    private final Map<String, Double> skillXp;
    
    // Constructor for new players
    public SkyblockProfile(UUID uuid) {
        this.playerUUID = uuid;
        this.coins = 0.0;
        this.bankBalance = 0.0;
        this.statMultipliers = new EnumMap<>(StatType.class);
        this.skillLevels = new HashMap<>();
        this.skillXp = new HashMap<>();
        
        // Initialize default stats
        for (StatType stat : StatType.values()) {
            statMultipliers.put(stat, 0.0);
        }
    }

    // Getters
    public UUID getUuid() { return playerUUID; }
    public double getCoins() { return coins; }
    public double getBankBalance() { return bankBalance; }

    // Logic
    public void addCoins(double amount) {
        this.coins += amount;
    }

    public boolean removeCoins(double amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        }
        return false;
    }

    public double getStatBonus(StatType type) {
        return statMultipliers.getOrDefault(type, 0.0);
    }

    public void setStatBonus(StatType type, double value) {
        statMultipliers.put(type, value);
    }
    
    public void addSkillXp(String skillName, double xp) {
        this.skillXp.put(skillName, this.skillXp.getOrDefault(skillName, 0.0) + xp);
        // Level up logic will be handled by the Skills module events
    }

    // Serialization (Saving to Disk)
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putUuid("OwnerUUID", playerUUID);
        tag.putDouble("Coins", coins);
        tag.putDouble("Bank", bankBalance);

        NbtCompound statsTag = new NbtCompound();
        statMultipliers.forEach((stat, val) -> statsTag.putDouble(stat.name(), val));
        tag.put("Stats", statsTag);

        NbtCompound skillsTag = new NbtCompound();
        skillXp.forEach(skillsTag::putDouble);
        tag.put("Skills", skillsTag);

        return tag;
    }

    // Deserialization (Loading from Disk)
    public static SkyblockProfile fromNbt(NbtCompound tag) {
        UUID uuid = tag.getUuid("OwnerUUID");
        SkyblockProfile profile = new SkyblockProfile(uuid);
        
        profile.coins = tag.getDouble("Coins");
        profile.bankBalance = tag.getDouble("Bank");

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
