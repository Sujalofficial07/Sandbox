package com.sujal.skyblock.core.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerProfileData {
    // Core Stats (Base values)
    private final Map<String, Double> baseStats = new HashMap<>();
    
    // Resources
    private double currentHealth;
    private double currentMana;
    
    // Progression
    private final Set<Identifier> unlockedLocations = new HashSet<>();
    private Identifier privateIslandId;
    private double purseCoinsVisual = 0.0;

    public PlayerProfileData() {
        // Defaults simulating a new SkyBlock profile
        baseStats.put("health", 100.0);
        baseStats.put("defense", 0.0);
        baseStats.put("mana", 100.0);
        baseStats.put("strength", 0.0);
        baseStats.put("crit_chance", 30.0);
        baseStats.put("crit_damage", 50.0);
        
        currentHealth = 100.0;
        currentMana = 100.0;
    }

    public void writeNbt(NbtCompound nbt) {
        NbtCompound statsTag = new NbtCompound();
        baseStats.forEach(statsTag::putDouble);
        nbt.put("BaseStats", statsTag);

        nbt.putDouble("CurrentHealth", currentHealth);
        nbt.putDouble("CurrentMana", currentMana);
        nbt.putDouble("PurseVisual", purseCoinsVisual);

        if (privateIslandId != null) {
            nbt.putString("PrivateIsland", privateIslandId.toString());
        }

        NbtList locList = new NbtList();
        unlockedLocations.forEach(loc -> locList.add(NbtString.of(loc.toString())));
        nbt.put("UnlockedLocations", locList);
    }

    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("BaseStats")) {
            NbtCompound statsTag = nbt.getCompound("BaseStats");
            for (String key : statsTag.getKeys()) {
                baseStats.put(key, statsTag.getDouble(key));
            }
        }

        if (nbt.contains("CurrentHealth")) currentHealth = nbt.getDouble("CurrentHealth");
        if (nbt.contains("CurrentMana")) currentMana = nbt.getDouble("CurrentMana");
        if (nbt.contains("PurseVisual")) purseCoinsVisual = nbt.getDouble("PurseVisual");
        
        if (nbt.contains("PrivateIsland")) {
            privateIslandId = new Identifier(nbt.getString("PrivateIsland"));
        }

        if (nbt.contains("UnlockedLocations")) {
            NbtList list = nbt.getList("UnlockedLocations", NbtElement.STRING_TYPE);
            for (int i = 0; i < list.size(); i++) {
                unlockedLocations.add(new Identifier(list.getString(i)));
            }
        }
    }

    // Getters and Setters logic
    public double getBaseStat(String key) { return baseStats.getOrDefault(key, 0.0); }
    public void setBaseStat(String key, double val) { baseStats.put(key, val); }
    
    public double getCurrentMana() { return currentMana; }
    public void setCurrentMana(double mana) { this.currentMana = mana; }
    
    public double getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(double health) { this.currentHealth = health; }

    public Set<Identifier> getUnlockedLocations() { return unlockedLocations; }
}
