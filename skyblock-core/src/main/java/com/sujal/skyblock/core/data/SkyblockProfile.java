package com.sujal.skyblock.core.data;

import com.sujal.skyblock.core.api.StatType;
import net.minecraft.nbt.NbtCompound;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkyblockProfile {
    private final UUID playerUUID;
    private double coins;
    private double currentMana;
    private final Map<StatType, Double> statMultipliers;
    private final Map<String, Double> skillXp;
    private long lastManaRegenTick;

    public SkyblockProfile(UUID uuid) {
        this.playerUUID = uuid;
        this.coins = 0.0;
        this.currentMana = 100.0;
        this.statMultipliers = new EnumMap<>(StatType.class);
        this.skillXp = new HashMap<>();
        this.lastManaRegenTick = 0;

        // Initialize default stats
        for (StatType stat : StatType.values()) {
            statMultipliers.put(stat, 0.0);
        }
    }

    public UUID getUuid() { return playerUUID; }
    public double getCoins() { return coins; }
    public double getCurrentMana() { return currentMana; }

    public void addCoins(double amount) { this.coins += amount; }
    
    // Mana Logic
    public boolean consumeMana(double amount) {
        if (this.currentMana >= amount) {
            this.currentMana -= amount;
            return true;
        }
        return false;
    }

    public void regenMana(double maxMana) {
        // Regen 2% of max mana per second. 
        // Logic should be called every tick, so we add (2% / 20)
        double regenPerTick = (maxMana * 0.02) / 20.0;
        this.currentMana = Math.min(maxMana, this.currentMana + regenPerTick);
    }

    public double getStatBonus(StatType type) {
        return statMultipliers.getOrDefault(type, 0.0);
    }

    public void setStatBonus(StatType type, double value) {
        statMultipliers.put(type, value);
    }

    public void addSkillXp(String skillName, double xp) {
        this.skillXp.put(skillName, this.skillXp.getOrDefault(skillName, 0.0) + xp);
    }

    // NBT Serialization
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putUuid("OwnerUUID", playerUUID);
        tag.putDouble("Coins", coins);
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
        UUID uuid = tag.getUuid("OwnerUUID");
        SkyblockProfile profile = new SkyblockProfile(uuid);
        
        profile.coins = tag.getDouble("Coins");
        if (tag.contains("CurrentMana")) {
            profile.currentMana = tag.getDouble("CurrentMana");
        }

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
